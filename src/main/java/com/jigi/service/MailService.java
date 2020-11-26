package com.jigi.service;

import com.jigi.web.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log
public class MailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "heyjigi@gmail.com";

    public void mailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        log.info("----메일 보낸다.->"+mailDto.getAddress());
        mailSender.send(message);
    }
}
