package com.alphacfter.journalApp.controller;

import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//Request Mapping is the parent of the exposed endpoint soo all end points within
//the body of this class will proceed via this endpoint
@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    /**
     * createUser creates a new user requesting from the user body itself from the frontend (Postman)
     * @param user is the user variable from the response body(Postman)
     */
    @PostMapping
    public void createNewUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    /**
     * updateUser generates or changes a username and password if required
     * @param user user field input by an API frontend (Postman)
     */
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String username){
        User userInDB = userService.findByUsername(username);
        if(userInDB != null){
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            userService.saveEntry(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
