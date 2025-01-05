package com.alphacfter.journalApp.service;

import com.alphacfter.journalApp.controller.JournalEntryController;
import com.alphacfter.journalApp.entity.JournalEntry;
import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
     * Saves a journal entry for a specific user into the database.
     *
     * <ol>
     *   <li>First, the username is determined from the database using {@code User userInDB}.</li>
     *   <li>Second, the local time is fetched from the computer or operating system
     *       and set into the {@code setDate} field of the journal entry.</li>
     *   <li>Third, the data is saved into the journal repository using {@code JournalEntry saved}.</li>
     *   <li>Since there is a list in the {@code User} class {@link User#getJournalEntry()},
     *       the journal entry is added, and a reference is shared in the user's journal list.</li>
     *   <li>Lastly, the method registers the updated user into the database using
     *       {@link UserService#saveNewUser(User)}.</li>
     * </ol>
     *
     * This method is transactional, meaning it ensures atomicity for the entire operation.
     * To enable transactional support, ensure the main class has the annotation
     * {@code @EnableTransactionalManagement}, such as in
     * {@link com.alphacfter.journalApp.JournalApplication}.
     *
     * @param journalEntry the body of the JSON sent from the frontend (e.g., Postman).
     * @param username     the username retrieved from the URL, which is used to associate
     *                     the journal entry with the specific user.
     */
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try{
            User userInDB = userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            userInDB.getJournalEntry().add(saved);
            userService.saveUser(userInDB);
        }catch (Exception e){
            throw new RuntimeException("Error has occurred" + e);
        }
    }


    /**
     * Overloaded method to handle various parameters {@link JournalEntryController#updateJournalByID(ObjectId, JournalEntry, String)}
     * @param journalEntry returns the body of the JSON sent from frontend(Postman)
     */
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findEntryByID(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteEntryByID(ObjectId id, String username){
        boolean isRemoved = false;
        try {
            User userInDB = userService.findByUsername(username);
            isRemoved = userInDB.getJournalEntry().removeIf(x -> x.getId().equals(id));
            if(isRemoved){
                userService.saveUser(userInDB);
                journalEntryRepo.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error Deleting entry"+e.getMessage());
        }
        return isRemoved;
    }
}
