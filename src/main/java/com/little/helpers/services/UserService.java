package com.little.helpers.services;


import com.little.helpers.exceptions.*;
import com.little.helpers.models.MyUserDetails;
import com.little.helpers.models.User;
import com.little.helpers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private String errorMsg;

    public List<User> listAll() {
        return repo.findAll();
    }

    public UserDetails loadUserByUsername (String emailAddress) throws UserNotFound {

            Optional<User> user = repo.findByEmailAddress(emailAddress);
            user.orElseThrow(()-> new UserNotFound());
            return user.map(MyUserDetails::new).get();
    }

    public void searchUserAlready (String emailAddress) throws EmailAlreadyExists {
        try {
            loadUserByUsername(emailAddress);
            throw new EmailAlreadyExists(emailAddress);
        }catch(UserNotFound ignored){}
    }


    public void newUserAccount(User std) throws IOException {

        try {
            if (std.getFirstName().length() < 2 || std.getLastName().length() < 2)
                throw new CompleteAllFields();
            Encryption.checkEmailStructure(std.getEmailAddress());
            Encryption.checkPasswordStrength(std.getPassword());
            searchUserAlready(std.getEmailAddress());
            User newU = new User(std.getFirstName() , std.getLastName() , std.getEmailAddress() ,Encryption.encryptString(std.getPassword()),"user");
            repo.save(newU);
        } catch (CompleteAllFields | EmailNotValid | EmailAlreadyExists | NotStrongPassword e) {
            setErrorMsg(e.getMessage());
        }
    }

    public User getUserByID(int id) {
        return repo.findById(id).get();
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

}
