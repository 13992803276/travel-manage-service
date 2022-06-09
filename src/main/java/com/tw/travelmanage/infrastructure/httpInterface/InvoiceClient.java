package com.tw.travelmanage.infrastructure.httpInterface;


import com.tw.travelmanage.controller.configuration.FeignConfiguration;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceRequest;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lexu
 */
@FeignClient(name = "invoice-service", configuration = FeignConfiguration.class, url = "https://xx.xx.com/invoice")
public interface InvoiceClient {
    @PostMapping(value = "/v1/invoice")
    InvoiceResponse applyInvoice(@RequestBody InvoiceRequest invoiceRequest);
}
