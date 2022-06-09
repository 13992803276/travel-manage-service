package com.tw.travelmanage.infrastructure.httpInterface;


import com.tw.travelmanage.controller.configuration.FeignConfiguration;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeRequest;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lexu
 */
@FeignClient(name = "bank-payment-service", configuration = FeignConfiguration.class, url = "https://xx.xx.com/pay-charge")
public interface FixedChargeClient {
    @PostMapping(value = "/v1/bank/charge")
    FixedChargeResponse payment(@RequestBody FixedChargeRequest fixedChargeRequest);
}
