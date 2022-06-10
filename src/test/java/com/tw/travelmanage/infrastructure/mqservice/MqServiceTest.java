package com.tw.travelmanage.infrastructure.mqservice;

import com.tw.travelmanage.infrastructure.mqService.kafka.KafkaSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MqServiceTest {

    @InjectMocks
    KafkaSender kafkaSender;

    @Mock
    private  KafkaTemplate<String, String> kafkaTemplate;

    @Captor
    ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    public void test_mq_service_sender_can_receive_correct_parameters(){
        kafkaSender.send("a invoice info message is send successful","invoice");
        verify(kafkaTemplate,times(1)).send(any(),argumentCaptor.capture());
        String value = argumentCaptor.getValue();
        Assertions.assertTrue(value.contains("a invoice info message is send successful"));
    }

    @Test
    public void test_mq_sender_should_return_message_after_send_messages(){
        Mockito.when(kafkaTemplate.send(anyString(),any())).thenReturn(null);
        String message = kafkaSender.send("message","invoice");
        Assertions.assertEquals(message,"message");
    }
}
