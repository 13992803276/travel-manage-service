package com.tw.travelmanage.util.mapstruct;

import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeRequest;
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

    public static Invoice invoiceResponseToInvoice(InvoiceResponse invoiceResponse) {
        return Invoice.builder()
                .amount(invoiceResponse.getAmount())
                .invoiceDate(invoiceResponse.getInvoiceDate())
                .invoiceName(invoiceResponse.getInvoiceName())
                .payerAddress(invoiceResponse.getPayerAddress())
                .payerBankNo(invoiceResponse.getPayerBankNo())
                .saleBankNo(invoiceResponse.getSaleBankNo())
                .saleTaxNo(invoiceResponse.getSaleTaxNo())
                .build();
    }
}
