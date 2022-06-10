package com.tw.travelmanage.infrastructure.httpInterface.httpentity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author lexu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private String code;
    private String  msg;
    private InvoiceData data;
}