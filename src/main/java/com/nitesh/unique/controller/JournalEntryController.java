package com.nitesh.unique.controller;

import com.nitesh.unique.entity.JournalEntry;
import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.services.JournalEntriesService;
import com.nitesh.unique.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntriesService journalEntriesService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        UserEntry user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("{userName}")
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {

        journalEntry.setDate(LocalDateTime.now());
        journalEntriesService.saveEntity(journalEntry, userName);
        return journalEntry;
    }

    @GetMapping("/id/{myId}")
    public Optional<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        return Optional.ofNullable(journalEntriesService.findById(myId).orElse(null));
    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteDataById(@PathVariable ObjectId myId, @PathVariable String userName) {
        journalEntriesService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping ("id/{userName}/{myId}")
    public ResponseEntity <?> updateJournalById
            (@PathVariable ObjectId myId , @RequestBody JournalEntry newEntry, @PathVariable String userName){
        JournalEntry old = journalEntriesService.findById(myId).orElse(null);
        if(old!=null){
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


