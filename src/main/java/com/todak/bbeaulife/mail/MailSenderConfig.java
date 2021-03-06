package com.todak.bbeaulife.mail;

import com.todak.bbeaulife.config.DotenvLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    private final String username;
    private final String password;
    private final String host;
    private final String protocol;
    private final int port;
    private final String auth;
    private final String sslEnable;
    private final String encoding;

    public MailSenderConfig(DotenvLoader dotenvLoader) {
        this.username = dotenvLoader.getString("GMAIL_USERNAME");
        this.password = dotenvLoader.getString("GMAIL_PASSWORD");
        this.host = dotenvLoader.getString("GMAIL_HOST");
        this.port = dotenvLoader.getInteger("GMAIL_PORT");
        this.protocol = dotenvLoader.getString("GMAIL_PROTOCOL");
        this.auth = dotenvLoader.getString("GMAIL_AUTH");
        this.sslEnable = dotenvLoader.getString("GMAIL_SSL_ENABLE");
        this.encoding = dotenvLoader.getString("GMAIL_ENCODING");
    }

    @Bean
    public TemplateEngine htmlTemplateEngine() {
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(springResourceTemplateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver() {
        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setOrder(1);
        springResourceTemplateResolver.setPrefix("classpath:mail-template/");
        springResourceTemplateResolver.setSuffix(".html");
        springResourceTemplateResolver.setTemplateMode(TemplateMode.HTML);
        springResourceTemplateResolver.setCharacterEncoding("UTF-8");
        springResourceTemplateResolver.setCacheable(false);
        return springResourceTemplateResolver;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol(protocol);
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setPassword(password);
        javaMailSender.setUsername(username);
        javaMailSender.setDefaultEncoding(encoding);


        Properties props = javaMailSender.getJavaMailProperties();
        props.setProperty("mail.smtp.starttls.enable", this.sslEnable);
        props.setProperty("mail.smtp.auth", this.auth);
        return javaMailSender;
    }

}
