package com.nitesh.unique.controller;

import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserEntry users){
        userService.saveNewUser(users);
    }
}
