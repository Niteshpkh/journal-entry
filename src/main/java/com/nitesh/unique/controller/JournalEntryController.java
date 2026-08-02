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

  @GetMapping("/UserName")
public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String UserName){
    UserEntry user = userService.findByUserName(UserName);
      List <JournalEntry> all = user.getJournalEntries();
      if(all!=null && !all.isEmpty()){
          return  new ResponseEntity<>(all, HttpStatus.OK);

      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}


@PostMapping("{userName}")
public JournalEntry createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName){

      journalEntry.setDate(LocalDateTime.now());
     journalEntriesService.saveEntity(journalEntry, userName);
     return journalEntry;
}
@GetMapping("/id/{myId}")
public Optional<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
      return Optional.ofNullable(journalEntriesService.findById(myId).orElse(null));
}
@DeleteMapping("/id/{myId}")
public boolean deleteDataById(@PathVariable ObjectId myId, @PathVariable String userName){
       journalEntriesService.deleteById(myId, userName);
       return true;
}
@PutMapping("/id/{id}")
    public JournalEntry insertDataByMyId(@PathVariable ObjectId id, @RequestBody JournalEntry myEntry){
      return null;
}
}
