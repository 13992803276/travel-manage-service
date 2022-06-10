package com.tw.travelmanage.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lexu
 */
@RestController
@RequestMapping(value ="/hello")
@Api(tags = "测试接口")
public class TestController {
    @GetMapping(value ="/test")
    public String test(){
        return "connection is success";
    }
}



