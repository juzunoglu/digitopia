package com.example.demo.kafka.producer;

import com.example.demo.kafka.events.EMailEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailProducer {

    private final NewTopic topic;

    private final KafkaTemplate<String, EMailEvent> kafkaTemplate;


    public EmailProducer(NewTopic topic, KafkaTemplate<String, EMailEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(EMailEvent eMailEvent) {
        log.info(eMailEvent.toString());
        Message<EMailEvent> message = MessageBuilder
                .withPayload(eMailEvent)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
    }
}
