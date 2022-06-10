package com.tw.travelmanage.controller;

import com.tw.travelmanage.constant.RespondStatus;
import com.tw.travelmanage.service.TravelAgreementService;
import com.tw.travelmanage.service.datamodel.PayFixFeeDataModel;
import com.tw.travelmanage.util.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class TravelAgreementControllerTest {

    @Autowired
    MockMvc mockMvc ;

    @MockBean
    TravelAgreementService travelAgreementService;

    PayFixFeeDataModel payFixFeeDataModel = PayFixFeeDataModel.builder()
            .payAmount(BigDecimal.valueOf(100.00))
            .payStatus("2")
            .payTitle("差旅服务固定费用")
            .payer("xule")
            .build();

    @Test
    public void given_a_stayPay_fixed_statement_when_success_should_return_200() throws Exception {
        Mockito.when(travelAgreementService.payFixedFees(any())).thenReturn(payFixFeeDataModel);
        mockMvc.perform(post("http://localhost:8080/travel_service_contract/fixedfee/12/confirmation")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    @Test
    public void given_a_stayPay_fixed_statement_when_service_throw_exception_should_be_return_500() throws Exception {
        Mockito.when(travelAgreementService.payFixedFees(any())).thenThrow(new BusinessException(RespondStatus.ERROR));
        mockMvc.perform(post("http://localhost:8080/travel_service_contract/fixedfee/12/confirmation")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError())
                .andReturn()
                .getResponse();
    }

    @Test
    public void given_a_stayPay_fixed_statement_when_double_payed_should_be_return_402() throws Exception {
        Mockito.when(travelAgreementService.payFixedFees(any())).thenThrow(new BusinessException(RespondStatus.DOUBLE_PAYMENT));
        mockMvc.perform(post("http://localhost:8080/travel_service_contract/fixedfee/12/confirmation")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isPaymentRequired())
                .andReturn()
                .getResponse();
    }
}