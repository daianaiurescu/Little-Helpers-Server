package com.little.helpers.services;


import com.little.helpers.exceptions.*;
import com.little.helpers.models.User;
import com.little.helpers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> listAll() {
        return repo.findAll();
    }

    public User searchUserbyEmail (String emailAddress) throws UserNotFound {

        for(User i : repo.findAll())
            if(Objects.equals(emailAddress,i.getEmailAddress()))
                return i;
        throw new UserNotFound();
    }

    public void searchUserAlready (String emailAddress) throws EmailAlreadyExists {
        try {
            searchUserbyEmail(emailAddress);
            throw new EmailAlreadyExists(emailAddress);
        }catch(UserNotFound ignored){}
    }

    public void save(User std) throws IOException {

        try {
            if (std.getFirstName().length() < 2 || std.getLastName().length() < 2)
                throw new CompleteAllFields();
            Encryption.checkEmailStructure(std.getEmailAddress());
            Encryption.checkPasswordStrength(std.getPassword());
            searchUserAlready(std.getEmailAddress());
            User newU = new User(std.getFirstName() , std.getLastName() , std.getEmailAddress() , Encryption.encryptString(std.getEmailAddress(),std.getPassword()),"user");
            repo.save(newU);
        } catch (CompleteAllFields | EmailNotValid | EmailAlreadyExists | NotStrongPassword e) {}
    }

    public User get(int id) {
        return repo.findById(id).get();
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}
