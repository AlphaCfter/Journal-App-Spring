package com.alphacfter.journalApp.service;

import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Since SpringBootApplication was providing beans at runtime, @SpringBootApplication is not defined in
 * this package so so such bean @UserRepository is created will result in java.lang.NullPointerException.
 * Annotation with SpringBootTest will mark this package as a test package
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user){
        assertTrue(userService.saveNewUser(user));
    }


    /**
     *   @BeforeEach: Runs before each individual test. Used for initializing resources required for the test.
     *   @BeforeAll: Runs once before all tests. Used for global setup, like initializing shared resources or starting servers.
     *   @AfterEach: Runs after each individual test. Used for cleaning up resources after the test.
     *   @AfterAll: Runs once after all tests. Used for global cleanup, like closing connections or stopping servers.
    */

//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "1,6,3",
//            "3,3,9"
//    })
//    public void test(int a, int b, int expected) {
//        assertEquals(expected, a+b);
//    }
}
