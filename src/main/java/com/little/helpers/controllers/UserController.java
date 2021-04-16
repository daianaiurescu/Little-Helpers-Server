package com.little.helpers.controllers;

import com.little.helpers.models.User;
import com.little.helpers.repositories.UserRepository;
import com.little.helpers.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class UserController {

    @Autowired
     private UserRepository repo;

    @Autowired
    private UserService service;

    @GetMapping("/Home")
    public String welcome() {
        return "Welcome! :)";
    }

    @GetMapping("/Users")
    public List<User> getUsers() {
        return repo.findAll();
    }

    @GetMapping("/LoginUser")
    public String LoginUSer() {
        return "User Login";
    }

    @PostMapping("/Save")
    public ResponseEntity<String> SaveUser(@RequestBody User user) {
        try {
            service.save(user);
            return new ResponseEntity<>("Registration complete", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
