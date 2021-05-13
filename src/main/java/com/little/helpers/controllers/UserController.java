package com.little.helpers.controllers;

import com.little.helpers.exceptions.EmailNotValid;
import com.little.helpers.exceptions.NotStrongPassword;
import com.little.helpers.exceptions.UserTokenNotFound;
import com.little.helpers.models.*;
import com.little.helpers.repositories.UserRepository;
import com.little.helpers.services.Encryption;
import com.little.helpers.services.JwtUtil;
import com.little.helpers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/Home")
    public String welcome() {
        return "Welcome! :)";
    }

    @GetMapping("/Users")
    public List<User> getUsers() {
        return repo.findAll();
    }


    @GetMapping("/User/{id}")
    public UserPublic getUser(@PathVariable("id") String id) {
        int idInt = Integer.parseInt(id);

        User userDB = service.getUserByID(idInt);
        UserPublic userPublic = new UserPublic(
                userDB.getId(),
                userDB.getFirstName(),
                userDB.getLastName(),
                userDB.getEmailAddress(),
                userDB.getRole(),
                userDB.getTokens().get(userDB.getTokens().size() - 1).getToken());
        return userPublic;
    }
    @PostMapping("/ChangePassword")
    public ResponseEntity<String> changePassword(@RequestBody AuthRequest authRequest) {
        service.changeUserPassword(authRequest.getUserName(),authRequest.getPassword());
        if (service.getErrorMsg().length()==0) {
            return new ResponseEntity<>("Password changed", HttpStatus.CREATED);
        }
           return new ResponseEntity<>(service.getErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/Save")
    public ResponseEntity<String> SaveUser(@RequestBody User user) throws IOException {
        service.newUserAccount(user);
        if (service.getErrorMsg().length()==0)
            return new ResponseEntity<>("Registration Complete", HttpStatus.CREATED);
            return new ResponseEntity<>(service.getErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/Authenticate")
    public Object createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), Encryption.encryptString(authRequest.getPassword()))
            );

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        UserDetails userDetails = service.loadUserByUsername(authRequest.getUserName());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        service.assignToken(authRequest.getUserName(), token);
        Optional<User> userDB = repo.findByEmailAddress(authRequest.getUserName());
        UserPublic userPublic = new UserPublic(userDB.get().getId(),
                userDB.get().getFirstName(),
                userDB.get().getLastName(),
                userDB.get().getEmailAddress(),
                userDB.get().getRole(),
                token);

        return userPublic;

    }

    @GetMapping("/user/token")
    public UserPublic findByToken(@RequestHeader(value = "Authorization") String s) {
        String t = s.substring(7);
        service.unauthorizedUser(t);
        User userDB = service.findByToken(t);
        return new UserPublic(
                userDB.getId(),
                userDB.getFirstName(),
                userDB.getLastName(),
                userDB.getEmailAddress(),
                userDB.getRole(),
                t);
    }

    @DeleteMapping("/user/logout")
    public UserPublic logoutUser(@RequestHeader(value = "Authorization") String token) {
        String t = token.substring(7);
        User userDB = service.findByToken(t);
        try {
            service.deleteUserToken(t);
        } catch (UserTokenNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found", e);
        }
        return new UserPublic(
                userDB.getId(),
                userDB.getFirstName(),
                userDB.getLastName(),
                userDB.getEmailAddress(),
                userDB.getRole(),
                null);
    }
    @PostMapping("/ChangeUserDetails")
    public void changeUserDetails(@RequestBody User user) throws EmailNotValid {
        service.changeUserDetails(user.getFirstName(), user.getLastName(), user.getEmailAddress());
    }
}
