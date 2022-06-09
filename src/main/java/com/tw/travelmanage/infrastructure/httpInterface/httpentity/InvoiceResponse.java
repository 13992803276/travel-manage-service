package com.tw.travelmanage.infrastructure.httpInterface.httpentity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author lexu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private String payerName;
    private BigDecimal amount;
    private String payerTaxNo;
    private String payerAddress;
    private String payerBankNo;
    private String saleTaxNo;
    private String saleAddress;
    private String saleBankNo;
    private LocalDate invoiceDate;
    private String invoiceName;
}
