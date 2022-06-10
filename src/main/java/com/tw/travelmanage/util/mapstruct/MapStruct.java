package com.tw.travelmanage.util.mapstruct;

import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeRequest;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceData;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceResponse;
import com.tw.travelmanage.infrastructure.repository.entity.FixedStatement;
import com.tw.travelmanage.infrastructure.repository.entity.Invoice;

/**
 * @author lexu
 */
public class MapStruct {
    public static FixedChargeRequest fixedStatementToFixedChargeRequest(FixedStatement fixedStatement){
        return FixedChargeRequest.builder()
                .amount(fixedStatement.getAmount())
                .beneBankName(fixedStatement.getBeneBankName())
                .beneficiary(fixedStatement.getBeneficiary())
                .beneBankNo(fixedStatement.getBeneBankNo())
                .remitBankName(fixedStatement.getRemitBankName())
                .remittance(fixedStatement.getRemittance())
                .remitBankNo(fixedStatement.getRemitBankNo())
                .remitterName(fixedStatement.getRemitter())
                .build();
    }

    public static Invoice invoiceDataToInvoice(InvoiceData invoiceData) {
        return Invoice.builder()
                .amount(invoiceData.getAmount())
                .invoiceDate(invoiceData.getInvoiceDate())
                .invoiceName(invoiceData.getInvoiceName())
                .payerAddress(invoiceData.getPayerAddress())
                .payerBankNo(invoiceData.getPayerBankNo())
                .saleBankNo(invoiceData.getSaleBankNo())
                .saleTaxNo(invoiceData.getSaleTaxNo())
                .build();
    }
}
