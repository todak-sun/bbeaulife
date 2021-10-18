package com.todak.bbeaulife.mail;

import com.todak.bbeaulife.config.DotenvLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    private final String username;
    private final String password;
    private final String host;
    private final String protocol;
    private final int port;
    private final boolean auth;
    private final boolean sslEnable;
    private final String sslTrust;
    private final String encoding;

    public MailSenderConfig(DotenvLoader dotenvLoader) {
        this.username = dotenvLoader.getString("GMAIL_USERNAME");
        this.password = dotenvLoader.getString("GMAIL_PASSWORD");
        this.host = dotenvLoader.getString("GMAIL_HOST");
        this.port = dotenvLoader.getInteger("GMAIL_PORT");
        this.protocol = dotenvLoader.getString("GMAIL_PROTOCOL");
        this.auth = dotenvLoader.getBoolean("GMAIL_AUTH");
        this.sslEnable = dotenvLoader.getBoolean("GMAIL_SSL_ENABLE");
        this.sslTrust = dotenvLoader.getString("GMAIL_SSL_TRUST");
        this.encoding = dotenvLoader.getString("GMAIL_ENCODING");
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

//        javaMailSender.setHost();

        Properties properties = javaMailSender.getJavaMailProperties();
//        properties.setProperty("")

        return javaMailSender;
    }

}
