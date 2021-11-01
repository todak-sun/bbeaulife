package com.todak.bbeaulife.mail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class MailHandler {

    private final JavaMailSender javaMailSender;
    private final MimeMessage mimeMessage;
    private final MimeMessageHelper mimeMessageHelper;

    @Qualifier("htmlTemplateEngine")
    private TemplateEngine templateEngine;

    public MailHandler(JavaMailSender javaMailSender) throws MessagingException {
        this.javaMailSender = javaMailSender;
        this.mimeMessage = this.javaMailSender.createMimeMessage();
        this.mimeMessageHelper = new MimeMessageHelper(this.mimeMessage, true, "UTF-8");
    }

    public void send() throws MessagingException {

        this.mimeMessageHelper.setFrom("todaksun@gmail.com");
        this.mimeMessageHelper.setTo("tjsdydwn@naver.com");
        this.mimeMessageHelper.setSubject("회원가입을 진심으로 축하드립니다.");
        this.mimeMessageHelper.setSentDate(new Date());
        ClassPathResource classPathResource = new ClassPathResource("mail-template/content.html");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))) {
            String html = br.lines().collect(Collectors.joining());
            this.mimeMessageHelper.setText(html, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.javaMailSender.send(this.mimeMessageHelper.getMimeMessage());
    }


}
