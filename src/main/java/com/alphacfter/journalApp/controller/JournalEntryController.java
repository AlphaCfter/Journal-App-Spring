package com.alphacfter.journalApp.controller;

import com.alphacfter.journalApp.entity.JournalEntry;
import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.service.JournalEntryService;
import com.alphacfter.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;



//Request Mapping is the parent of the exposed endpoint soo all end points within
//the body of this class will proceed via this endpoint
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    //Instance of a user service is now given to IOC where objects are generated using AAutowires
    @Autowired
    private JournalEntryService journalEntryService;

    //Instance of the user service is now given to IOC where objects are generated using AAutowires
    @Autowired
    private UserService userService;

    /**
     * Retrieves all journal entries for the authenticated user.
     *
     * Example: GET request to `localhost:8080/journal`
     *
     * @return a ResponseEntity containing:
     *         - A list of all journal entries and HTTP status 200 (OK) if entries exist for the user.
     *         - An empty list and HTTP status 404 (NOT FOUND) if no entries are found for the user.
     */
    @GetMapping
    public ResponseEntity<?> getAllUserEntries(){  //localhost:8080/journal
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDB = userService.findByUsername(username);
        List<JournalEntry> all = userInDB.getJournalEntry();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(all,HttpStatus.NOT_FOUND);
    }


    //When specific endpoints are not declared near the POST mapping, the java application
    //Simply won't compile due to collision in the logic since every endpoint within the
    //class was exposed

    /**
     * Creates a new journal entry based on the provided JSON data.
     *
     * Example: POST request to `localhost:8080/journal` with a JSON body.
     *
     * @param myEntry the journal entry data provided in the request body of type `JournalEntry`
     * @return a ResponseEntity containing:
     *         - The created journal entry and HTTP status 201 (CREATED) if the operation is successful.
     *         - HTTP status 400 (BAD REQUEST) if an error occurs during the creation process.
     *
     * Note: The `@RequestBody` annotation indicates that the method expects the journal entry data
     *       in the request body, typically in JSON format.
     */
    @PostMapping
    public ResponseEntity<JournalEntry> createUserEntry(@RequestBody JournalEntry myEntry){  //localhost:8080/journal
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * Retrieves a journal entry by its ID.
     *
     * Example: GET request to `localhost:8080/journal/id/2`
     *
     * @param myID the ID of the journal entry to retrieve from the database
     * @return a ResponseEntity containing:
     *         - The journal entry and HTTP status 200 (OK) if the journal entry is found
     *           and belongs to the authenticated user.
     *         - HTTP status 404 (NOT FOUND) if the journal entry with the given ID does not exist
     *           or does not belong to the authenticated user.
     */
    @GetMapping("id/{myID}")
    public ResponseEntity<?> getJournalByID(@PathVariable ObjectId myID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDB = userService.findByUsername(username);
        List<JournalEntry> collect = userInDB.getJournalEntry().stream().filter(x -> x.getId().equals(myID)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findEntryByID(myID);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a journal entry by its ID.
     *
     * Example: DELETE request to `localhost:8080/journal/id/2`
     *
     * @param myID the ID of the journal entry to delete from the database
     * @return a ResponseEntity containing:
     *         - HTTP status 204 (NO CONTENT) if the journal entry is successfully deleted.
     *         - HTTP status 404 (NOT FOUND) if the journal entry with the given ID does not exist
     *           or does not belong to the authenticated user.
     *
     * Note: The wildcard `?` in ResponseEntity allows returning any type of response body,
     *       though this method currently returns an empty body.
     */
    @DeleteMapping("id/{myID}")
    public ResponseEntity<?> deleteJournalByID(@PathVariable ObjectId myID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isRemoved = journalEntryService.deleteEntryByID(myID, username);
        if(isRemoved){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates a journal entry by its ID with the provided JSON body.
     *
     * Example: PUT request to `localhost:8080/journal/id/2`
     *
     * @param myID  the ID of the journal entry to update in the database
     * @param entry the updated journal entry data provided in the request body
     * @return a ResponseEntity containing:
     *         - The updated journal entry and HTTP status 200 (OK) if the update is successful.
     *         - HTTP status 404 (NOT FOUND) if the journal entry with the given ID does not exist
     *           or does not belong to the authenticated user.
     *
     * Note: The title and content are updated only if they are not null and not empty.
     */
    @PutMapping("id/{myID}")
    public ResponseEntity<?> updateJournalByID(@PathVariable ObjectId myID, @RequestBody JournalEntry entry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDB = userService.findByUsername(username);
        List<JournalEntry> collect = userInDB.getJournalEntry().stream().filter(x -> x.getId().equals(myID)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findEntryByID(myID);
            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle() : old.getTitle());
                old.setContent(entry.getContent() != null && !entry.getTitle().equals("") ? entry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
