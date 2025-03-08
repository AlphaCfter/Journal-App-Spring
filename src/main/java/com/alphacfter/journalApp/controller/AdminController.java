package com.alphacfter.journalApp.controller;

import com.alphacfter.journalApp.cache.AppCache;
import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        if(allUsers != null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody User user){
        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * Clears the application cache by reinitializing it.
     *
     * <p>This method is exposed as a GET endpoint that, when accessed, will clear the current cache and
     * repopulate it with fresh configuration data from the database. This is useful when the underlying
     * configuration data has changed, and the cache needs to be updated to reflect those changes.</p>
     *
     * @see AppCache#init()
     */
    @GetMapping("clear-app-cache")
    public void clearAppCache() {
        appCache.init();
    }

}
