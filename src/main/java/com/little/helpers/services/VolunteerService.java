package com.little.helpers.services;

import com.little.helpers.exceptions.*;
import com.little.helpers.models.User;
import com.little.helpers.models.Volunteer;
import com.little.helpers.repositories.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class VolunteerService {


    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Autowired
    VolunteerRepository repo;

    public void newVolunteer(Volunteer volunteer) throws IOException {
        try {

            if(volunteer.getFirstName().equals("First name...") ||
                    volunteer.getLastName().equals("Last name...") ||
                    volunteer.getPhone().equals("") ||
                    volunteer.getEmailAddress().equals("Email address...") ||
                    volunteer.getDescription().equals(""))
                throw new CompleteAllFields();
            if (volunteer.getFirstName().length() < 2 || volunteer.getLastName().length() < 2 || volunteer.getDescription().length() < 2 || (volunteer.getPhone().length()!=10 && volunteer.getPhone().contains("0123456789")))
                throw new CompleteAllFields();
            Encryption.checkEmailStructure(volunteer.getEmailAddress());
            if(repo.findByEmailAddress(volunteer.getEmailAddress()).isEmpty()) {
                Volunteer newVolunteer = new Volunteer(volunteer.getFirstName(), volunteer.getLastName(), volunteer.getBirthday(), volunteer.getPhone(), volunteer.getEmailAddress(), volunteer.getDescription(), volunteer.getApplied_at());
                repo.save(newVolunteer);
            }
            else
                throw new EmailAlreadyExists(volunteer.getEmailAddress());

        } catch (CompleteAllFields | EmailNotValid | EmailAlreadyExists e) {
            setErrorMsg(e.getMessage());
        }

    }
}
