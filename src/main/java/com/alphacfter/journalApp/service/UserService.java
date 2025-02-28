package com.alphacfter.journalApp.service;

import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

//controller --calls--> services --calls--> repository
@Component
@Slf4j
public class UserService {

    
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder encodePassword = new BCryptPasswordEncoder();

    /**
     * Encodes the user's password and saves the new user to the database.
     *
     * This method uses {@link BCryptPasswordEncoder} to securely encode the user's password before saving.<br>
     * <p>
     * Related Methods:<ol>
     * <li>{@link com.alphacfter.journalApp.controller.UserEntryController#updateUser(User)}: Updates existing user credentials.</li>
     * <li>{@link com.alphacfter.journalApp.controller.PublicController#createNewUser(User)}: Creates new users upon request.</li></ol>
     * @param user the user object containing the credentials to be encrypted and saved
     */
    public boolean saveNewUser(User user){
        try{
            user.setPassword(encodePassword.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error("Invalid username for {}: ",user.getUsername(),e);
            return false;
        }

    }

    public void saveAdmin(User user) {
        user.setPassword(encodePassword.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUserByID(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }


}
