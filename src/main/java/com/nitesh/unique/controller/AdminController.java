package com.nitesh.unique.controller;

import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntry> allUsers = userService.getAllUsers();
        if(allUsers !=null && !allUsers.isEmpty())
        {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping ("/create_admin")
    public void createAdmin(@RequestBody UserEntry user){
        System.out.println("Before: " + user.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Before: " + user.getRoles());
        userService.saveAdminUser(user);
    }
}
