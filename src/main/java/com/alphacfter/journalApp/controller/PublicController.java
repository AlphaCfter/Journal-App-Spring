package com.alphacfter.journalApp.controller;

import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    /**
     * createUser creates a new user requesting from the user body itself from the frontend (Postman)
     * @param user is the user variable from the response body(Postman)
     */
    @PostMapping("/create-user")
    public void createNewUser(@RequestBody User user){
        userService.saveEntry(user);
    }
}
