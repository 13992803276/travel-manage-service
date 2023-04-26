package com.tw.travelmanage.infrastructure.httpInterface;

import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceRequest;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.InvoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lexu
 */
@Service
public class InvoiceApplyService {

    @Autowired
    InvoiceClient invoiceClient;

    public InvoiceResponse applyInvoice(InvoiceRequest invoiceRequest) {
        return invoiceClient.applyInvoice(invoiceRequest);
    }
}
