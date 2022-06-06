package com.tw.travelmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lexu
 */
@SpringBootApplication
@EnableFeignClients
public class TravelManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelManageServiceApplication.class, args);
    }

}
