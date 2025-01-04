package com.alphacfter.journalApp.service;

import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
 public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInDB = userRespository.findByUsername(username);
        if(userInDB != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userInDB.getUsername())
                    .password(userInDB.getPassword())
                    .roles(userInDB.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("Username not found "+username);
    }
}
