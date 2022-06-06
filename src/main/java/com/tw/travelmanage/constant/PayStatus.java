package com.tw.travelmanage.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lexu
 */
@Getter
@AllArgsConstructor
public enum PayStatus {
    STAY_PAY("0"), PAYING("1"), PAID_SUCCESS("2"), PAID_FAILURE("3");
    private final String code;

}
