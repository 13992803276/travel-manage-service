package com.tw.travelmanage.infrastructure.mqservice.mqentity;

import lombok.Builder;
import lombok.Data;

/**
 * @author lexu
 */

@Data
@Builder
public class ConsumerResponse {
    private String records;
    private String message;
}
