package com.tw.travelmanage.infrastructure.httpInterface;

import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceRequest;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceResponse;
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
public class InvoiceApplyServiceTest {

    @InjectMocks
    InvoiceApplyService invoiceApplyService;

    @Mock
    InvoiceClient invoiceClient;

    @Captor
    ArgumentCaptor<InvoiceRequest> argumentCaptor = ArgumentCaptor.forClass(InvoiceRequest.class);

    @Test
    public void given_invoice_apply_call_client_should_return_success() {
        InvoiceResponse invoiceResponse = getInvoiceResponse();
        Mockito.when(invoiceClient.applyInvoice(any())).thenReturn(invoiceResponse);
        InvoiceResponse response = invoiceApplyService.applyInvoice(getInvoiceRequest());
        Assertions.assertEquals(invoiceResponse.getCode(), response.getCode());
        Assertions.assertEquals(invoiceResponse.getMsg(), response.getMsg());
    }

    @Test
    public void test_when_call_invoice_client_to_apply_invoice_should_receive_correct_parameters() {
        invoiceApplyService.applyInvoice(getInvoiceRequest());
        verify(invoiceClient, times(1)).applyInvoice(argumentCaptor.capture());
        Assertions.assertEquals(BigDecimal.valueOf(100.00), argumentCaptor.getValue().getAmount());
        Assertions.assertEquals("陕西省西安市", argumentCaptor.getValue().getCompanyAddress());
        Assertions.assertEquals("思特沃克", argumentCaptor.getValue().getCompanyName());
        Assertions.assertEquals("123456", argumentCaptor.getValue().getTaxpayerNo());
    }

    public static InvoiceRequest getInvoiceRequest() {
        return InvoiceRequest.builder()
                .amount(BigDecimal.valueOf(100.00))
                .companyAddress("陕西省西安市")
                .companyName("思特沃克")
                .taxpayerNo("123456")
                .build();
    }

    public static InvoiceResponse getInvoiceResponse() {
        return InvoiceResponse.builder()
                .code("200")
                .msg("success")
                .data(null)
                .build();
    }
}