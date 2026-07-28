package com.nitesh.unique.controller;

import com.nitesh.unique.entity.JournalEntry;
import com.nitesh.unique.services.JournalEntriesService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    public JournalEntriesService journalEntriesService;


  @GetMapping
public List<JournalEntry> getAll(){
    return journalEntriesService.getAll();
}
@PostMapping
public JournalEntry createEntry(@RequestBody JournalEntry myEntry, Locale locale){
      myEntry.setDate(LocalDateTime.now());
     journalEntriesService.saveEntity(myEntry);
     return myEntry;
}
@GetMapping("/id/{myId}")
public Optional<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
      return Optional.ofNullable(journalEntriesService.findById(myId).orElse(null));
}
@DeleteMapping("/id/{myId}")
public boolean deleteDataById(@PathVariable ObjectId myId){
       journalEntriesService.deleteById(myId);
       return true;
}
@PutMapping("/id/{id}")
    public JournalEntry insertDataByMyId(@PathVariable ObjectId id, @RequestBody JournalEntry myEntry){
      return null;
}
}
