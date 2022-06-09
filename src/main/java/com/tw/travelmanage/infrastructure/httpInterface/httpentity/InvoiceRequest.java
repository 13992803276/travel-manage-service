package com.tw.travelmanage.infrastructure.httpInterface.httpentity;

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
public class InvoiceRequest {
    private String taxpayerNo;
    private String companyName;
    private String companyAddress;
    private BigDecimal amount;
}
