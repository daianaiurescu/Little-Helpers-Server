package com.little.helpers.services;


import com.little.helpers.exceptions.*;
import com.little.helpers.models.MyUserDetails;
import com.little.helpers.models.Organisation;
import com.little.helpers.models.User;
import com.little.helpers.models.UserToken;
import com.little.helpers.repositories.UserRepository;
import com.little.helpers.repositories.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserTokenRepository userTokenRepository;

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
    public User searchUserByEmail(String emailAddress) throws UserNotFound {
          try {
              Optional<User> user = repo.findByEmailAddress(emailAddress);
              if (user.isEmpty())
                  throw new UserNotFound();
              return user.get();
          } catch (UserNotFound e) {
              setErrorMsg(e.getMessage());
          }
          return null;
    }
    public User searchUserByFullName(String firstName) throws UserNotFound{
        try {
            Optional<User> user = repo.findByFirstName(firstName);
            if (user.isEmpty())
                throw new UserNotFound();
            return user.get();
        } catch (UserNotFound e) {
            setErrorMsg(e.getMessage());
        }
        return null;
    }
    public void changeUserPassword(String email , String password) {
        try {
                errorMsg = "";
                if(email.isEmpty() || password.isEmpty())
                    throw new CompleteAllFields();
                Encryption.checkEmailStructure(email);
                Encryption.checkPasswordStrength(password);
                User user = this.searchUserByEmail(email);
                user.setPassword(Encryption.encryptString(password));
                repo.save(user);
        } catch (CompleteAllFields | EmailNotValid | NotStrongPassword e) {
            setErrorMsg(e.getMessage());
        }
    }
    public void searchUserAlready (String emailAddress) throws EmailAlreadyExists {
        try {
            loadUserByUsername(emailAddress);
            throw new EmailAlreadyExists(emailAddress);
        }catch(UserNotFound ignored){}
    }


    public void newUserAccount(User std) throws IOException {

        try {
            errorMsg = "";
            if (std.getFirstName().length() < 2 || std.getLastName().length() < 2)
                throw new CompleteAllFields();
            Encryption.checkEmailStructure(std.getEmailAddress());
            Encryption.checkPasswordStrength(std.getPassword());
            searchUserAlready(std.getEmailAddress());
            User newU = new User(std.getFirstName() , std.getLastName() , std.getEmailAddress() ,Encryption.encryptString(std.getPassword()),std.getRole());
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

    public User assignToken(String userName, String token) {
        User u = repo.findByEmailAddress(userName).get();
        UserToken t = new UserToken();
        t.setToken(token);
        List<UserToken> ut = u.getTokens();
        ut.add(t);
        return repo.save(u);
    }
    public User findByToken(String token) {
        UserToken t = userTokenRepository.findByToken(token);
        return repo.findByTokens(t);
    }
    public void unauthorizedUser(String token) {
        if (!userTokenRepository.existsByToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid auth token");
        }
    }
    public void deleteUserToken(String token) {
        UserToken userToken = userTokenRepository.findByToken(token);
        try {
            userTokenRepository.deleteById(userToken.getId());
        } catch (Exception e) {
            throw new UserTokenNotFound(String.format("Token %s not found", token));
        }
    }
    public void changeUserDetails(String firstName, String lastName, String email) throws EmailNotValid {
        try {
            User user = this.searchUserByFullName(firstName);
            Encryption.checkEmailStructure(email);
            user.setEmailAddress(email);
            user.setLastName(lastName);
            repo.save(user);
        }catch(EmailNotValid e){
            setErrorMsg(e.getMessage());
        }
    }
}
