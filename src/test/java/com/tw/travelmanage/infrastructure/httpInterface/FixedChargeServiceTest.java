package com.tw.travelmanage.infrastructure.httpInterface;

import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeRequest;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FixedChargeServiceTest {
    @InjectMocks
    FixedChargeService fixedChargeService;
    @Mock
    FixedChargeClient fixedChargeClient;

    @Captor
    ArgumentCaptor<FixedChargeRequest> argumentCaptor = ArgumentCaptor.forClass(FixedChargeRequest.class);

    @Test
    public void given_fixedChargeRequest_call_client_should_return_success() {
        FixedChargeResponse fixedChargeResponse = getFixedChargeResponse();
        Mockito.when(fixedChargeClient.payment(any())).thenReturn(fixedChargeResponse);
        FixedChargeResponse response = fixedChargeService.payment(getFixedChargeRequest());
        Assertions.assertEquals("200", response.getCode());
        Assertions.assertTrue(response.getMessage().contains("pay success"));
    }

    @Test
    public void test_when_call_client_to_charge_fixed_fee_should_receive_correct_parameters(){
        fixedChargeService.payment(getFixedChargeRequest());
        verify(fixedChargeClient, times(1)).payment(argumentCaptor.capture());
        Assertions.assertEquals(argumentCaptor.getValue().getAmount(), BigDecimal.valueOf(100.00));
        Assertions.assertEquals(argumentCaptor.getValue().getBeneBankName(), "中国银行");
        Assertions.assertEquals(argumentCaptor.getValue().getBeneBankNo(), "123456");
        Assertions.assertEquals(argumentCaptor.getValue().getBeneficiary(), "任你行平台");
    }

    public static FixedChargeRequest getFixedChargeRequest() {
        return FixedChargeRequest.builder()
                .amount(BigDecimal.valueOf(100.00))
                .beneBankName("中国银行")
                .beneBankNo("123456")
                .beneficiary("任你行平台")
                .remitBankName("招商银行")
                .remitBankNo("345678")
                .remittance("思特沃克")
                .build();
    }

    public static FixedChargeResponse getFixedChargeResponse() {
        return FixedChargeResponse.builder()
                .code("200")
                .message("pay success")
                .build();
    }
}

