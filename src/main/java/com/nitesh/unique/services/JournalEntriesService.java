package com.nitesh.unique.services;

import com.nitesh.unique.entity.JournalEntry;
import com.nitesh.unique.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntriesService {


    @Autowired
    private journalEntryRepository JournalEntryRepo;

    public void saveEntity(JournalEntry journalEntry){
        JournalEntryRepo.save(journalEntry);
    }
    public  List <JournalEntry> getAll(){
       return JournalEntryRepo.findAll();

    }
    public Optional<JournalEntry> findById(ObjectId id){
        return JournalEntryRepo.findById(id);

    }
    public boolean deleteById(ObjectId id){
       JournalEntryRepo.deleteById(id);
        return true;
    }
    public void updateData(@PathVariable ObjectId id , @RequestBody JournalEntry newEntry){
        Optional<JournalEntry> journalEntry = JournalEntryRepo.findById(id);
        if(journalEntry != null){

        }

    }

}
