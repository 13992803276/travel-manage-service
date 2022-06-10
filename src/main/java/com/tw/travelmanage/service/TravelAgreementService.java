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
public class TravelAgreementService {

    @Autowired
    FixedChargeService fixedChargeService;
    @Autowired
    InvoiceApplyService invoiceApplyService;
    @Autowired
    KafkaSender kafkaSender;
    @Autowired
    FixedStatementRepository fixedStatementRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    private static final Integer RETRY_TIMES = 3;

    public TravelAgreementService(FixedChargeService fixedChargeService, InvoiceApplyService invoiceApplyService, KafkaSender kafkaSender, FixedStatementRepository fixedStatementRepository, InvoiceRepository invoiceRepository) {
        this.invoiceApplyService = invoiceApplyService;
        this.fixedStatementRepository = fixedStatementRepository;
        this.fixedChargeService = fixedChargeService;
        this.kafkaSender = kafkaSender;
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public PayFixFeeDataModel payFixedFees(FixedFeeDto fixedFeeDto) {
        FixedStatement fixedStatementEntity = fixedStatementRepository.findFixedStatementById(fixedFeeDto.getFixedFeeId());
        if (PayStatus.STAY_PAY.getCode().equals(fixedStatementEntity.getPayStatus())) {
            FixedChargeResponse fixedChargeResponse = new FixedChargeResponse();
            FixedChargeRequest fixedChargeRequest = MapStruct.fixedStatementToFixedChargeRequest(fixedStatementEntity);
            String chargeResult = doCharge(fixedChargeRequest);
            log.info("first charge result is {}", chargeResult);
            if (!RespondStatus.SUCCESS.getCode().equals(chargeResult)) {
                chargeResult = cycleCharge(fixedChargeRequest);
            }
            log.info("the chargeResult is {} ", chargeResult);

            if (RespondStatus.SUCCESS.getCode().equals(chargeResult)) {
                saveFixedStatement(fixedStatementEntity);
            } else if (RespondStatus.BALANCE_ERROR.getCode().equals(chargeResult)) {
                throw new BusinessException(RespondStatus.BALANCE_ERROR);
            } else {
                throw new BusinessException(RespondStatus.ERROR);
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

    private String doCharge(FixedChargeRequest fixedChargeRequest) {
        String chargeResult = "";
        try {
            FixedChargeResponse fixedChargeResponse = fixedChargeService.payment(fixedChargeRequest);
            if (RespondStatus.SUCCESS.getCode().equals(fixedChargeResponse.getCode())) {
                chargeResult = RespondStatus.SUCCESS.getCode();
            } else if (RespondStatus.BALANCE_ERROR.getCode().equals(fixedChargeResponse.getCode())) {
                chargeResult = RespondStatus.BALANCE_ERROR.getCode();
            }
        } catch (Exception exception) {
            chargeResult = RespondStatus.ERROR.getCode();
        }
        return chargeResult;
    }

    private String cycleCharge(FixedChargeRequest fixedChargeRequest) {
        String tempResult = "";
        for (int i = 0; i < RETRY_TIMES; i++) {
            tempResult = doCharge(fixedChargeRequest);
        }
        return tempResult;
    }

    public String applyInvoice(Integer fixedId, InvoiceDto invoiceDto) {
        FixedStatement fixedStatement = fixedStatementRepository.findFixedStatementById(fixedId);
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
