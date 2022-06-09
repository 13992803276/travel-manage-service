package com.tw.travelmanage.infrastructure.mqService.mqEntity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author lexu
 */
@Data
public class Message {
    private Long id;
    private String msg;
    private LocalDate  sendTime;
}
