package com.alphacfter.journalApp.service;

import com.alphacfter.journalApp.entity.JournalEntry;
import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.repository.JournalRepository;
import com.alphacfter.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

//controller --calls--> services --calls--> repository
@Component
public class UserEntryService {

    /**
     * Autowired repo makes the particular body as a dependency injection
     */
    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUserByID(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteUserByID(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}