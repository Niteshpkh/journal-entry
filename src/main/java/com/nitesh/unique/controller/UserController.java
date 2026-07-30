package com.nitesh.unique.controller;

import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping
    public List<UserEntry> getAllUser(){
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody UserEntry users){
        userService.saveUser(users);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntry user){
       UserEntry UserInDB =  userService.findByUserName(user.getUserName());
       if(UserInDB != null){
           UserInDB.setUserName(user.getUserName());
           UserInDB.setPassword(user.getPassword());
           userService.saveUser(UserInDB);
       }
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
