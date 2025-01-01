package com.alphacfter.journalApp.controller;

import com.alphacfter.journalApp.entity.JournalEntry;
import com.alphacfter.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JournalEntry> getall(){  //localhost:8080/journal
            return journalEntryService.getAll();
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
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){  //localhost:8080/journal
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    //Any URL preceding with a ? indicates that a query parameter(Request Param)

    /**
     * Example : localhost:8080/journal/id/2
     *
     * @param myID Returns the ID of the record in the DB(function) in this case
     * @return Returns the journal entry by ID back to the call function
     */
    @GetMapping("id/{myID}")
    public JournalEntry getJournalByID(@PathVariable ObjectId myID){

        return journalEntryService.findEntryByID(myID).orElse(null);
    }

    /**Example : localhost:8080/journal/id/2
     * @param myID deletes the ID of the record in the DB(function) in this case
     * @return Returns the journal entry by ID back to the call function
     */
    @DeleteMapping("id/{myID}")
    public JournalEntry deleteJournalByID(@PathVariable ObjectId myID){

       return null;
    }

    /**Example : localhost:8080/journal/id/2
     * @param myID updates the ID of the record in the DB(function) in this case
     * @param entry updates the ID of a gives record with a given JSON body
     * @return Returns the journal entry by ID back to the call function
     */
    @PutMapping("id/{myID}")
    public JournalEntry deleteJournalByID(@PathVariable ObjectId myID, @RequestBody JournalEntry entry){
        return null;
    }
}
