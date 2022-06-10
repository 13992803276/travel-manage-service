package com.tw.travelmanage.controller.dto;

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
public class InvoiceDto {
    private String taxpayerNo;
    private String companyName;
    private String companyAddress;
    private BigDecimal amount;
}
