package com.alphacfter.journalApp.controller;

import com.alphacfter.journalApp.entity.JournalEntry;
import com.alphacfter.journalApp.service.JournalEntryService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


//Request Mapping is the parent of the exposed endpoint soo all end points within
//the body of this class will proceed via this endpoint
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    //Instance of a database is now given to IOC where objects are generated using AAutowires
    @Autowired
    private JournalEntryService journalEntryService;

    //Methods in controllers should be declared public so that the framework could
    //access it via the end point
    @GetMapping
    public ResponseEntity<?> getall(){  //localhost:8080/journal
        List<JournalEntry> all = journalEntryService.getAll();
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
     * @param myEntry Accept a JSON parameter from the API call of type JournalEntry
     * @return returns a Boolean statement of weather a record has been created
     */
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){  //localhost:8080/journal
        try{
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
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

        JournalEntry old = journalEntryService.findEntryByID(myID).orElse(null);
        if(old != null) {
            old.setTitle(entry.getTitle() != null && entry.getTitle().equals("") ? entry.getTitle() : old.getTitle());
            old.setContent(entry.getContent() != null && entry.getTitle().equals("") ? entry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
