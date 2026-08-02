package com.nitesh.unique.services;

import com.nitesh.unique.entity.JournalEntry;
import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntriesService {


    @Autowired
    private journalEntryRepository JournalEntryRepo;

    @Autowired
    private UserService userService;

    public void saveEntity(JournalEntry journalEntry, String userName){
        journalEntry.setDate(LocalDateTime.now());
        UserEntry user = userService.findByUserName(userName);
        JournalEntry saved = JournalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }
    public  List<JournalEntry> getAll(){
       return JournalEntryRepo.findAll();

    }
    public Optional<JournalEntry> findById(ObjectId id){
        return JournalEntryRepo.findById(id);

    }
    public boolean deleteById(ObjectId id, String userName){
        UserEntry user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.saveUser(user);
       JournalEntryRepo.deleteById(id);
        return true;
    }
    public void updateData(@PathVariable ObjectId id , @RequestBody JournalEntry newEntry){
        Optional<JournalEntry> journalEntry = JournalEntryRepo.findById(id);
        if(journalEntry != null){

        }

    }

}
