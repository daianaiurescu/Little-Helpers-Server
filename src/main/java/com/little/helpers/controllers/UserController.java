package com.little.helpers.controllers;

import com.little.helpers.exceptions.*;
import com.little.helpers.models.AuthRequest;
import com.little.helpers.models.User;
import com.little.helpers.repositories.UserRepository;
import com.little.helpers.services.Encryption;
import com.little.helpers.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class UserController {

    @Autowired
     private UserRepository repo;

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/Home")
    public String welcome() {
        return "Welcome! :)";
    }

    @GetMapping("/Users")
    public List<User> getUsers() {
        return repo.findAll();
    }


    @GetMapping("/User/{id}")
    public User getUser(@PathVariable("id") String id) {
        int idInt = Integer.parseInt(id);
        return service.getUserByID(idInt);}


   @PostMapping("/Save")
    public ResponseEntity<String> SaveUser(@RequestBody User user) throws IOException {
        service.newUserAccount(user);
        if (service.getErrorMsg().isEmpty())
            return new ResponseEntity<>("Registration Complete",HttpStatus.CREATED);
                return new ResponseEntity<>(service.getErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping ("/Authenticate")
    public Object LoginUSer(@RequestBody AuthRequest authRequest) {
        Optional<User> userDB = repo.findByEmailAddress(authRequest.getUserName());
        String authReqPassword = Encryption.encryptString(authRequest.getPassword());
        if(!(authRequest.getPassword().isEmpty() || authRequest.getUserName().isEmpty()) && authReqPassword.equals(userDB.get().getPassword()))
        return userDB;
        return new ResponseEntity<String>("Invalid username/password", HttpStatus.INTERNAL_SERVER_ERROR);


//    @PostMapping ("/Authenticate")
//    public Optional<User> LoginUSer(@RequestBody AuthRequest authRequest) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
//                            authRequest.getPassword())
//            );
//        } catch (Exception ex) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid username/password", ex);
//        }
//        Optional<User> userDB = repo.findByEmailAddress(authRequest.getUserName());
//        return userDB;
//    }
  
}
