package com.alphacfter.journalApp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class emailServiceImpl {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail() {

        Assertions.assertDoesNotThrow(() -> emailService.sendMail(
                "AlphaCfter@proton.me",
                "This is just for test",
                "Hello this is from SpringBoot"
        ));

    }
}
