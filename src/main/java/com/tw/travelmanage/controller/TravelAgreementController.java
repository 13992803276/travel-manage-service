package com.tw.travelmanage.controller;

import com.tw.travelmanage.controller.dto.FixedFeeDto;
import com.tw.travelmanage.controller.dto.InvoiceDto;
import com.tw.travelmanage.controller.dto.RespondEntity;
import com.tw.travelmanage.service.TravelAgreementService;
import com.tw.travelmanage.service.datamodel.PayFixFeeDataModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lexu
 */
@Slf4j
@RestController
@RequestMapping( "/travel_service_contract")
@Api(tags = "差旅服务协议服务合约业务接口")
public class TravelAgreementController {

    @Autowired
    TravelAgreementService travelAgreementService;

    @ApiOperation(value = "固定费用支付确认接口", notes = "通过固定费用缴费单ID进行支付", httpMethod = "POST")
    @PostMapping("/fixedfee/{fid}/confirmation")
    public RespondEntity<PayFixFeeDataModel> paymentConfirmation(@PathVariable String fid) {
        PayFixFeeDataModel payFixFeeDataModel = travelAgreementService.payFixedFees(FixedFeeDto.builder().fixedFeeId(Integer.valueOf(fid)).build());
        return RespondEntity.ok(payFixFeeDataModel);
    }

    @ApiOperation(value = "固定费用发票确认接口", notes = "根据缴费单ID以及发票信息申请发票", httpMethod = "POST")
    @PostMapping("/fixed_invoice/{fid}/confirmation")
    public RespondEntity<String> applyInvoices(@PathVariable String fid, @RequestBody InvoiceDto invoiceDto) {
        return RespondEntity.ok(travelAgreementService.applyInvoice(Integer.valueOf(fid),invoiceDto));
    }
}
