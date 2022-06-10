package com.tw.travelmanage.service;

import com.tw.travelmanage.controller.dto.FixedFeeDto;
import com.tw.travelmanage.infrastructure.httpInterface.FixedChargeService;
import com.tw.travelmanage.infrastructure.httpInterface.InvoiceApplyService;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeResponse;
import com.tw.travelmanage.infrastructure.mqService.kafka.KafkaSender;
import com.tw.travelmanage.infrastructure.repository.FixedStatementRepository;
import com.tw.travelmanage.infrastructure.repository.InvoiceRepository;
import com.tw.travelmanage.infrastructure.repository.entity.FixedStatement;
import com.tw.travelmanage.service.datamodel.PayFixFeeDataModel;
import com.tw.travelmanage.util.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class TravelAgreementServiceTest {

    private FixedChargeService fixedChargeService;
    private FixedStatementRepository fixedStatementRepository;
    private TravelAgreementService travelAgreementService;

    @BeforeEach
    public void setup() {
        fixedChargeService = Mockito.mock(FixedChargeService.class);
        InvoiceApplyService invoiceApplyService = Mockito.mock(InvoiceApplyService.class);
        KafkaSender kafkaSender = Mockito.mock(KafkaSender.class);
        fixedStatementRepository = Mockito.mock(FixedStatementRepository.class);
        InvoiceRepository invoiceRepository = Mockito.mock(InvoiceRepository.class);
        travelAgreementService = new TravelAgreementService(fixedChargeService, invoiceApplyService,
                kafkaSender, fixedStatementRepository, invoiceRepository);
    }

    @Test
    public void given_stay_payed_fixed_fee_when_pay_should_be_return_success() {
        FixedFeeDto fixedFeeDto = FixedFeeDto.builder().fixedFeeId(1).build();
        FixedStatement fixedStatementMock = getFixedStatementEntityMock("0");
        Mockito.when(fixedStatementRepository.getFixedStatementById(any())).thenReturn(fixedStatementMock);
        Mockito.when(fixedChargeService.payment(any())).thenReturn(FixedChargeResponse.builder().code("200").message("success").build());
        PayFixFeeDataModel payFixFeeDataModel = travelAgreementService.payFixedFees(fixedFeeDto);
        Assertions.assertEquals("2", payFixFeeDataModel.getPayStatus());
    }

    @Test
    public void given_payed_success_fixed_fee_when_pay_should_be_return_double_payed_exception() {
        FixedFeeDto fixedFeeDto = FixedFeeDto.builder().fixedFeeId(1).build();
        FixedStatement fixedStatementMock = getFixedStatementEntityMock("2");
        Mockito.when(fixedStatementRepository.getFixedStatementById(any())).thenReturn(fixedStatementMock);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            travelAgreementService.payFixedFees(fixedFeeDto);
        }, "the payment has payed");
        Assertions.assertEquals("402", exception.getRs().getCode());
        Assertions.assertTrue(exception.getRs().getMessage().contains("the payment has payed"));
    }

    @Test
    public void given_stay_payed_fixed_fee_when_charge_client_error_should_be_return_system_error_exception() {
        FixedFeeDto fixedFeeDto = FixedFeeDto.builder().fixedFeeId(1).build();
        FixedStatement fixedStatementMock = getFixedStatementEntityMock("0");
        Mockito.when(fixedStatementRepository.getFixedStatementById(any())).thenReturn(fixedStatementMock);
        Mockito.when(fixedChargeService.payment(any())).thenReturn(FixedChargeResponse.builder().code("500").message("system error").build());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            travelAgreementService.payFixedFees(fixedFeeDto);
        }, "system error");
        Assertions.assertEquals("500", exception.getRs().getCode());
        Assertions.assertTrue(exception.getRs().getMessage().contains("system error"));
    }

    @Test
    public void given_stay_payed_fixed_fee_when_remitBank_money_not_enough_should_be_return_business_error_exception() {
        FixedFeeDto fixedFeeDto = FixedFeeDto.builder().fixedFeeId(1).build();
        FixedStatement fixedStatementMock = getFixedStatementEntityMock("0");
        Mockito.when(fixedStatementRepository.getFixedStatementById(any())).thenReturn(fixedStatementMock);
        Mockito.when(fixedChargeService.payment(any())).thenReturn(FixedChargeResponse.builder().code("401").message("the balance is not enough").build());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            travelAgreementService.payFixedFees(fixedFeeDto);
        }, "the balance is not enough");
        Assertions.assertEquals("401", exception.getRs().getCode());
        Assertions.assertTrue(exception.getRs().getMessage().contains("the balance is not enough"));
    }

    public static FixedStatement getFixedStatementEntityMock(String payStatus) {
        return FixedStatement.builder()
                .amount(BigDecimal.valueOf(100.00))
                .beneBankName("中国银行")
                .beneBankNo("1234")
                .beneficiary("2344534")
                .payStatus(payStatus)
                .created(LocalDate.now())
                .remitBankName("招商银行")
                .remitBankNo("234534")
                .remittance("思特沃克")
                .remitter("徐乐")
                .remitTime(LocalDateTime.now())
                .title("差旅服务固定费用")
                .build();
    }
}