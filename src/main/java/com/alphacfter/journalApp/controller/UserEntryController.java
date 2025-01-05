package com.alphacfter.journalApp.controller;

import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.repository.UserRepository;
import com.alphacfter.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//Request Mapping is the parent of the exposed endpoint soo all end points within
//the body of this class will proceed via this endpoint
@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    /**
     * Updates the currently authenticated user and all their associated data.
     *
     * Example: PUT request to `localhost:8080/user`
     *
     * @return a ResponseEntity containing:
     *         - HTTP status 204 (NO CONTENT) if the user is successfully updated.
     *
     */
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDB = userService.findByUsername(username);
        userInDB.setUsername(user.getUsername());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes the currently authenticated user and all their associated data.
     *
     * Example: DELETE request to `localhost:8080/user`
     *
     * @return a ResponseEntity containing:
     *         - HTTP status 204 (NO CONTENT) if the user is successfully deleted.
     *
     */
    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
