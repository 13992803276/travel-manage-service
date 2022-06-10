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
import com.tw.travelmanage.infrastructure.repository.TravelUserRepository;
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

    @Autowired
    FixedChargeService fixedChargeService;
    @Autowired
    InvoiceApplyService invoiceApplyService;
    @Autowired
    KafkaSender kafkaSender;
    @Autowired
    TravelUserRepository travelUserRepository;
    @Autowired
    FixedStatementRepository fixedStatementRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    private static final Integer RETRY_TIMES = 3;

    @Transactional(rollbackOn = Exception.class)
    public PayFixFeeDataModel payFixedFees(FixedFeeDto fixedFeeDto) {
        FixedStatement fixedStatementDataModel = fixedStatementRepository.getFixedStatementById(fixedFeeDto.getFixedFeeId());
        if (PayStatus.STAY_PAY.getCode().equals(fixedStatementDataModel.getPayStatus())) {
            try {
                FixedChargeRequest fixedChargeRequest = MapStruct.fixedStatementToFixedChargeRequest(fixedStatementDataModel);
                FixedChargeResponse fixedChargeResponse = fixedChargeService.payment(fixedChargeRequest);
                log.info("the charge result is {}", fixedChargeResponse);
                if (RespondStatus.SUCCESS.getCode().equals(fixedChargeResponse.getCode())) {
                    saveFixedStatement(fixedStatementDataModel);
                } else if (RespondStatus.BALANCE_ERROR.getCode().equals(fixedChargeResponse.getCode())) {
                    throw new BusinessException(RespondStatus.BALANCE_ERROR);
                } else if (RespondStatus.ERROR.getCode().equals(fixedChargeResponse.getCode())) {
                    String code = cycleCharge(fixedChargeRequest);
                    if (RespondStatus.SUCCESS.getCode().equals(code)) {
                        saveFixedStatement(fixedStatementDataModel);
                    } else {
                        throw new BusinessException(RespondStatus.ERROR);
                    }
                }
            } catch (Exception e) {
                log.info("system error message is {}", e.getMessage());
                throw new BusinessException(RespondStatus.ERROR);
            }
        } else {
            throw new BusinessException(RespondStatus.DOUBLE_PAYMENT);
        }
        return PayFixFeeDataModel.builder()
                .payAmount(fixedStatementDataModel.getAmount())
                .payer(fixedStatementDataModel.getRemitter())
                .payStatus(fixedStatementDataModel.getPayStatus())
                .payTitle(fixedStatementDataModel.getTitle())
                .build();
    }

    private void saveFixedStatement(FixedStatement fixedStatementDataModel) {
        fixedStatementDataModel.setPayStatus(PayStatus.PAID_SUCCESS.getCode());
        fixedStatementDataModel.setRemitTime(LocalDateTime.now());
        fixedStatementRepository.save(fixedStatementDataModel);
    }

    private String cycleCharge(FixedChargeRequest fixedChargeRequest) {
        String codeTemp = RespondStatus.PARAM_ERROR.getCode();
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
        if(!PayStatus.PAID_SUCCESS.getCode().equals(fixedStatement.getPayStatus())){
            throw new BusinessException(RespondStatus.PARAM_ERROR);
        }
        InvoiceResponse invoiceResponse = invoiceApplyService.applyInvoice(null);
        if(RespondStatus.ERROR.getCode().equals(invoiceResponse.getCode())){
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
