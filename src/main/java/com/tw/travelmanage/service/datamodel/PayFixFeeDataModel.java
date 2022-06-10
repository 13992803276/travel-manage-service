package com.tw.travelmanage.service.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author lexu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayFixFeeDataModel {
    private String payStatus;
    private BigDecimal payAmount;
    private String payer;
    private String payTitle;
}
