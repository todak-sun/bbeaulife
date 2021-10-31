package com.todak.bbeaulife.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailHandler {

    private final JavaMailSender sender;

    public void send() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("tjsdydwn@naver.com");
        message.setFrom("tjsdydwn@gmail.com");
        message.setSubject("title");
        message.setText("text");
        sender.send(message);
    }


}
