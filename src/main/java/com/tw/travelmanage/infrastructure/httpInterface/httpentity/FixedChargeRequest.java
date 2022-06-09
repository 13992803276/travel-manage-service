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
public class FixedChargeRequest {
    private String remittance;
    private BigDecimal amount;
    private String remitBankName;
    private String remitBankNo;
    private String beneficiary;
    private String beneBankName;
    private String beneBankNo;
    private String remitterName;
}
