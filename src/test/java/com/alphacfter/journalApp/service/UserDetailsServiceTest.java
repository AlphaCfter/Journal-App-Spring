package com.alphacfter.journalApp.service;

import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

/**
 * Unit test for {@link UserDetailsServiceImp}.
 * <p>
 * This class tests the behavior of the {@code loadUserByUsername} method
 * in the {@link UserDetailsServiceImp} class using Mockito.
 * </p>
 */
public class UserDetailsServiceTest {

    /** Injects the mock dependencies into {@link UserDetailsServiceImp}. */
    @InjectMocks
    private UserDetailsServiceImp userDetailsService;

    /** Mocked instance of {@link UserRepository}. */
    @Mock
    private UserRepository userRepository;

    /** Initializes mocks before each test execution. */
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@code loadUserByUsername} method to verify that
     * it correctly retrieves user details from the repository.
     */
    @Test
    void loadUserByUsernameTest(){

        //Arrange
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("ram").roles(new ArrayList<>()).password("afegg").build());
        //act
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        //assert
        Assertions.assertNotNull(user);
    }
}
