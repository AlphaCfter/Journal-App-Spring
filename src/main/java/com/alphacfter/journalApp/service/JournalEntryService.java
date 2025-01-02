package com.alphacfter.journalApp.service;

import com.alphacfter.journalApp.entity.JournalEntry;
import com.alphacfter.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
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
