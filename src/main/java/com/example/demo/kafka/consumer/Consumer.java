package com.example.demo.kafka.consumer;

import com.example.demo.kafka.events.EMailEvent;
import com.example.demo.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Consumer {

    private final EmailService emailService;

    private static final String SUBJECT = "INVITATION";

    public Consumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(EMailEvent eMailEvent) {
        log.info("CONSUMED message: {}", eMailEvent.toString());
        try {
            emailService.sendEmail(eMailEvent.getEmail(), SUBJECT, eMailEvent.getInvitationMessage());
        } catch (MailException e) {
            log.error(e.toString());
        }
    }
}
