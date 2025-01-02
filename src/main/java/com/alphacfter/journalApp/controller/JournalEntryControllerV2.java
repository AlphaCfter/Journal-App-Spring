package com.alphacfter.journalApp.controller;

import com.alphacfter.journalApp.entity.JournalEntry;
import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.service.JournalEntryService;
import com.alphacfter.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


//Request Mapping is the parent of the exposed endpoint soo all end points within
//the body of this class will proceed via this endpoint
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    //Instance of a user service is now given to IOC where objects are generated using AAutowires
    @Autowired
    private JournalEntryService journalEntryService;

    //Instance of the user service is now given to IOC where objects are generated using AAutowires
    @Autowired
    private UserService userService;

    //Methods in controllers should be declared public so that the framework could
    //access it via the end point
    @GetMapping("{username}")
    public ResponseEntity<?> getAllUserEntries(@PathVariable String username){  //localhost:8080/journal
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
     * createsEntry based on the API call. RequestBody is an annotation used to tell
     * the compiler to receive a body mainly on raw/JSON type
     * - user is first fetch from the database and send it to service
     * @param myEntry Accept a JSON parameter from the API call of type JournalEntry
     * @param username Accepts a string type datatype to find a particular username
     * @return returns a Boolean statement of weather a record has been created
     */
    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createUserEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){  //localhost:8080/journal
        try{
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //Any URL preceding with a ? indicates that a query parameter(Request Param)

    /**
     * Example : localhost:8080/journal/id/2
     *
     * @param myID Returns the ID of the record in the DB(function) in this case
     * @return Returns the response code along with the journal entry
     */
    @GetMapping("id/{myID}")
    public ResponseEntity<?> getJournalByID(@PathVariable ObjectId myID){

        Optional<JournalEntry> journalEntry = journalEntryService.findEntryByID(myID);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**Example : localhost:8080/journal/id/2
     * @param myID deletes the ID of the record in the DB(function) in this case
     * @return Returns the status code of the current executing API
     * ResponseEntity<> where ? acts as a wildcard where any class can be returned in place of fixed class
     */
    @DeleteMapping("id/{myID}")
    public ResponseEntity<?> deleteJournalByID(@PathVariable ObjectId myID){
        journalEntryService.deleteEntryByID(myID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**Example : localhost:8080/journal/id/2
     * @param myID updates the ID of the record in the DB(function) in this case
     * @param entry updates the ID of a gives record with a given JSON body
     * @return Returns the journal entry by ID back to the call function
     * returns only if the title is not null or the title contains empty string
     */
    @PutMapping("id/{myID}")
    public ResponseEntity<?> updateJournalByID(@PathVariable ObjectId myID, @RequestBody JournalEntry entry){

//        JournalEntry old = journalEntryService.findEntryByID(myID).orElse(null);
//        if(old != null) {
//            old.setTitle(entry.getTitle() != null && entry.getTitle().equals("") ? entry.getTitle() : old.getTitle());
//            old.setContent(entry.getContent() != null && entry.getTitle().equals("") ? entry.getContent() : old.getContent());
//            journalEntryService.saveEntry(old);
//            return new ResponseEntity<>(old,HttpStatus.OK);
//        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
