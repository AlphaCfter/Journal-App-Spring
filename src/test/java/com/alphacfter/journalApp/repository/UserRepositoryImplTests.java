package com.alphacfter.journalApp.repository;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    public UserRepositoryImpl userRepository;

    @Test
    public void testSaveUserName(){
        Assertions.assertNotNull(userRepository.getUsersForSentimentAnalysis());
    }

}
