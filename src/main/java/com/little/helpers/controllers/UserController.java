package com.little.helpers.controllers;

import com.little.helpers.models.User;
import com.little.helpers.repositories.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class UserController {

    @Autowired
     private UserRepository repo;
    @GetMapping("/Home")
    public String welcome() {
        return "Welcome! :)";
    }

    @GetMapping("/Users")
    public List<User> getUsers() {
        return repo.findAll();
    }
}
