package com.tw.travelmanage.infrastructure.httpInterface;

import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeRequest;
import com.tw.travelmanage.infrastructure.httpInterface.httpentity.FixedChargeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lexu
 */
@Service
public class FixedChargeService {
    @Autowired
    FixedChargeClient fixedChargeClient;

    public FixedChargeResponse payment(FixedChargeRequest fixedChargeRequest){
        return fixedChargeClient.payment(fixedChargeRequest);
    }
}
