package com.nitesh.unique.controller;

import com.nitesh.unique.entity.JournalEntry;
import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.services.JournalEntriesService;
import com.nitesh.unique.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntriesService journalEntriesService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntry.setDate(LocalDateTime.now());
        journalEntriesService.saveEntity(journalEntry, userName);
        return journalEntry;
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry user= userService.findByUserName(userName);
       List <JournalEntry> collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
           Optional <JournalEntry> journalEntry = journalEntriesService.findById(myId);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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


