package com.example.demo.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    SimpleMailMessage sendEmail(String to, String subject, String messageBody);

}
