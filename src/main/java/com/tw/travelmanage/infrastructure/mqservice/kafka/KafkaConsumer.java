package com.tw.travelmanage.infrastructure.mqservice.kafka;

import com.tw.travelmanage.infrastructure.mqservice.mqentity.ConsumerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author lexu
 */
@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = {"invoice"})
    public ConsumerResponse listen(ConsumerRecord<?, ?> record) {
        Optional.ofNullable(record.value())
                .ifPresent(message -> {
                    log.info("【+++++++++++++++++ record = {} 】", record);
                    log.info("【+++++++++++++++++ message = {}】", message);
                });
        return ConsumerResponse.builder()
                .records(record.value().toString())
                .message(record.toString())
                .build();
    }
}
