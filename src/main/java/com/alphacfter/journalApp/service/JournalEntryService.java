package com.alphacfter.journalApp.service;

import com.alphacfter.journalApp.entity.JournalEntry;
import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//controller --calls--> services --calls--> repository
@Component
public class JournalEntryService {

    /**
     * Autowired repo makes the particular body as a dependency injection
     */
    @Autowired
    private JournalRepository journalEntryRepo;

    @Autowired
    private UserService userService;

    /**
     * First the username is determined from the database {@code User userInDB}
     * Second, local time is fetched from the computer or the operating system and pastes in to the setDate field
     * Third, the data is saved into the journal repository itself {@code JournalEntry saved}
     * Since there is a list in the user class {@link User#getJournalEntry()}, we could use {@code add}
     * and a part of reference is shared into the array list
     * Lastly the method is registered into the user database using the {@link UserService#saveEntry(User)}
     * @param journalEntry returns the body of the JSON sent from frontend(Postman)
     * @param username returns a String type username from the URL where it dumps the records in the specific username
     *
     */
    public void saveEntry(JournalEntry journalEntry, String username){
        User userInDB = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepo.save(journalEntry);
        userInDB.getJournalEntry().add(saved);
        userService.saveEntry(userInDB);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findEntryByID(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    public void deleteEntryByID(ObjectId id){
        journalEntryRepo.deleteById(id);
    }
}
