package com.tw.travelmanage.infrastructure.httpInterface;


import com.tw.travelmanage.controller.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author lexu
 */
@FeignClient(name = "wechat-payment-service", configuration = FeignConfiguration.class, url = "https://weixin.com/pay-ment")
public interface WechatPayClient {


}
