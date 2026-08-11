package com.nitesh.unique.controller;

import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntry user){
        userService.saveUser(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntry user){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
       UserEntry UserInDB =  userService.findByUserName(userName);
           UserInDB.setUserName(user.getUserName());
           UserInDB.setPassword(user.getPassword());
           userService.saveNewUser(UserInDB);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
