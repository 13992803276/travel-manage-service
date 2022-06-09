package com.tw.travelmanage.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lexu
 */
@Getter
@AllArgsConstructor
public enum RespondStatus {
    SUCCESS("200", "success"),
    PARAM_ERROR("400", "params is error"),
    BALANCE_ERROR("401", "the balance is not enough"),
    ERROR("505", "system error");

    private final String code;
    private final String message;
}
