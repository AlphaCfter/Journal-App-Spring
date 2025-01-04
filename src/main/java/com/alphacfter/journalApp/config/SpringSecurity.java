package com.alphacfter.journalApp.config;

import com.alphacfter.journalApp.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**<strong>Summary</strong>
 * <ol>
 * <li>A user submits their username and password</li>
 * <li>Spring Security invokes the {@link UserDetailsServiceImp#loadUserByUsername(String)} method from {@link UserDetailsServiceImp} to fetch user details from the database</li>
 * <li>The retrieved password (hashed) is compared with the submitted password (after encoding it with {@link BCryptPasswordEncoder})</li>
 * <li>If they match, the user is authenticated; otherwise, access is denied</li>
 * </ol>
 */
@Configuration
@EnableWebSecurity
public class SpringSecurity{

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;



    /**
     * Configures the HTTP filter chain to filter the incoming requests from the client
     * @param http http the {@link HttpSecurity} object used to configure HTTP security
     * @return returns an implementation of the <strong>built</strong> HTTP request
     * @throws Exception throws an exception which an error occurs during the build process
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(request -> request
                //.requestMatchers("/public/**").permitAll()
                .requestMatchers("/journal/**","/users/**").authenticated()
                //.requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    /**
     *  Configures global authentication by linking the custom {@link UserDetailsServiceImp} and setting the password encoder.
     * @param auth authenticates via the {@link AuthenticationManagerBuilder} used to configure authentication like hashing passwords
     * @throws Exception throws an exception when an error occours during the process
     */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp).passwordEncoder(passwordEncoder());
    }

    /**
     * A custom function to use {@link PasswordEncoder} to encode the password using an algorithm
     * In this case {@link BCryptPasswordEncoder}
     * @return an instance of {@link BCryptPasswordEncoder}
     */
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
