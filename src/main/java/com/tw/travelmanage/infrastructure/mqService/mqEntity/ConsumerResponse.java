package com.tw.travelmanage.infrastructure.mqService.mqEntity;

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
