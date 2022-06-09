package com.tw.travelmanage.util.exception;

import com.tw.travelmanage.constant.RespondStatus;

/**
 * @author lexu
 */
public class BusinessException extends RuntimeException{
    private final RespondStatus rs;

    public BusinessException(RespondStatus rs) {
        this.rs = rs;
    }

    public RespondStatus getRs() {
        return rs;
    }
}
