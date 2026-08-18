package com.nitesh.unique.services;

import com.nitesh.unique.entity.JournalEntry;
import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntriesService {


    @Autowired
    private journalEntryRepository JournalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntity(JournalEntry journalEntry, String userName){
            journalEntry.setDate(LocalDateTime.now());
            UserEntry user = userService.findByUserName(userName);
            JournalEntry saved = JournalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveNewUser(user);
    }
    public  List<JournalEntry> getAll(){
       return JournalEntryRepo.findAll();

    }
    public Optional<JournalEntry> findById(ObjectId id){
        return JournalEntryRepo.findById(id);

    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            UserEntry user = userService.findByUserName(userName);
            removed =  user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveNewUser(user);
                JournalEntryRepo.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            System.out.println(String.valueOf(e));
        }
        return removed;
    }

    public void saveEntry(JournalEntry journalEntry){
        JournalEntryRepo.save(journalEntry);
    }
    public List<JournalEntry> findByUserName(String UserName){
      return userService.findByUserName(UserName).getJournalEntries();
    }

}
