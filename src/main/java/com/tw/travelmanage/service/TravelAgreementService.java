package com.tw.travelmanage.service;

import com.tw.travelmanage.constant.PayStatus;
import com.tw.travelmanage.constant.RespondStatus;
import com.tw.travelmanage.controller.dto.FixedFeeDto;
import com.tw.travelmanage.controller.dto.InvoiceDto;
import com.tw.travelmanage.infrastructure.httpInterface.FixedChargeService;
import com.tw.travelmanage.infrastructure.httpInterface.InvoiceApplyService;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeRequest;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeResponse;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceData;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceResponse;
import com.tw.travelmanage.infrastructure.mqService.kafka.KafkaSender;
import com.tw.travelmanage.infrastructure.repository.FixedStatementRepository;
import com.tw.travelmanage.infrastructure.repository.InvoiceRepository;
import com.tw.travelmanage.infrastructure.repository.entity.FixedStatement;
import com.tw.travelmanage.infrastructure.repository.entity.Invoice;
import com.tw.travelmanage.service.datamodel.PayFixFeeDataModel;
import com.tw.travelmanage.util.exception.BusinessException;
import com.tw.travelmanage.util.mapstruct.MapStruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author lexu
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TravelAgreementService {


    private final FixedChargeService fixedChargeService;
    private final InvoiceApplyService invoiceApplyService;
    private final KafkaSender kafkaSender;
    private final FixedStatementRepository fixedStatementRepository;
    private final InvoiceRepository invoiceRepository;
    private static final Integer RETRY_TIMES = 3;


    @Transactional(rollbackOn = Exception.class)
    public PayFixFeeDataModel payFixedFees(FixedFeeDto fixedFeeDto) {
        FixedStatement fixedStatementEntity = fixedStatementRepository.getFixedStatementById(fixedFeeDto.getFixedFeeId());
        if (PayStatus.STAY_PAY.getCode().equals(fixedStatementEntity.getPayStatus())) {
            FixedChargeRequest fixedChargeRequest = MapStruct.fixedStatementToFixedChargeRequest(fixedStatementEntity);
            FixedChargeResponse fixedChargeResponse = fixedChargeService.payment(fixedChargeRequest);
            log.info("the charge result is {}", fixedChargeResponse);
            if (RespondStatus.SUCCESS.getCode().equals(fixedChargeResponse.getCode())) {
                saveFixedStatement(fixedStatementEntity);
            } else if (RespondStatus.BALANCE_ERROR.getCode().equals(fixedChargeResponse.getCode())) {
                throw new BusinessException(RespondStatus.BALANCE_ERROR);
            } else if (RespondStatus.ERROR.getCode().equals(fixedChargeResponse.getCode())) {
                String code = cycleCharge(fixedChargeRequest);
                if (RespondStatus.SUCCESS.getCode().equals(code)) {
                    saveFixedStatement(fixedStatementEntity);
                } else {
                    throw new BusinessException(RespondStatus.ERROR);
                }
            }
        } else {
            throw new BusinessException(RespondStatus.DOUBLE_PAYMENT);
        }
        return PayFixFeeDataModel.builder()
                .payAmount(fixedStatementEntity.getAmount())
                .payer(fixedStatementEntity.getRemitter())
                .payStatus(fixedStatementEntity.getPayStatus())
                .payTitle(fixedStatementEntity.getTitle())
                .build();
    }

    private void saveFixedStatement(FixedStatement fixedStatementDataModel) {
        fixedStatementDataModel.setPayStatus(PayStatus.PAID_SUCCESS.getCode());
        fixedStatementDataModel.setRemitTime(LocalDateTime.now());
        fixedStatementRepository.save(fixedStatementDataModel);
    }

    private String cycleCharge(FixedChargeRequest fixedChargeRequest) {
        String codeTemp = RespondStatus.ERROR.getCode();
        for (int i = 0; i < RETRY_TIMES; i++) {
            FixedChargeResponse charge = fixedChargeService.payment(fixedChargeRequest);
            if (RespondStatus.SUCCESS.getCode().equals(charge.getCode())) {
                codeTemp = RespondStatus.SUCCESS.getCode();
                break;
            }
        }
        return codeTemp;
    }

    public String applyInvoice(Integer fixedId, InvoiceDto invoiceDto) {
        FixedStatement fixedStatement = fixedStatementRepository.getFixedStatementById(fixedId);
        if (!PayStatus.PAID_SUCCESS.getCode().equals(fixedStatement.getPayStatus())) {
            throw new BusinessException(RespondStatus.PARAM_ERROR);
        }
        InvoiceResponse invoiceResponse = invoiceApplyService.applyInvoice(null);
        if (RespondStatus.ERROR.getCode().equals(invoiceResponse.getCode())) {
            kafkaSender.send(invoiceDto.toString(), "invoice");
        } else {
            saveInvoiceData(invoiceResponse.getData());
        }

        return RespondStatus.SUCCESS.getCode();
    }

    public void saveInvoiceData(InvoiceData invoiceData) {
        Invoice invoice = MapStruct.invoiceDataToInvoice(invoiceData);
        invoiceRepository.save(invoice);
    }
}
