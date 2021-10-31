package com.todak.bbeaulife.mail;

import com.todak.bbeaulife.config.WithContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class MailHandlerTest extends WithContainer {


    @Autowired
    MailHandler mailHandler;


    @Test
    void send_test() {
        mailHandler.send();
    }


}