package com.tw.travelmanage.infrastructure.mqService.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tw.travelmanage.infrastructure.mqService.mqentity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author lexu
 */
@Component
@Slf4j
public class KafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private final Gson gson = new GsonBuilder().create();

    public String send(String msg ,String topic) {
        Message message = new Message();

        message.setId(System.currentTimeMillis());
        message.setMsg(msg);
        message.setSendTime(LocalDate.now());
        log.info("【++++++++++++++++++ message ：{}】", gson.toJson(message));
        kafkaTemplate.send(topic, gson.toJson(message));
        return message.getMsg();
    }

}
