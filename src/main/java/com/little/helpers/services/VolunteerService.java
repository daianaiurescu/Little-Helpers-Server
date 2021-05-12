package com.little.helpers.services;

import com.little.helpers.exceptions.*;
import com.little.helpers.models.Organisation;
import com.little.helpers.models.User;
import com.little.helpers.models.Volunteer;
import com.little.helpers.repositories.OrganisationRepository;
import com.little.helpers.repositories.VolunteerRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    OrganisationRepository repoOrg;
    public void newVolunteer(Volunteer volunteer) throws IOException {
        try {
            errorMsg = "";
            if(volunteer.getFirstName().equals("First name...") ||
                    volunteer.getLastName().equals("Last name...") ||
                    volunteer.getPhone().equals("") ||
                    volunteer.getEmailAddress().equals("Email address...") ||
                    volunteer.getDescription().equals(""))
                throw new CompleteAllFields();
            if (volunteer.getFirstName().length() < 2 || volunteer.getLastName().length() < 2 || volunteer.getDescription().length() < 2 || (volunteer.getPhone().length()!=10 && volunteer.getPhone().contains("0123456789")))
                throw new CompleteAllFields();
            Encryption.checkEmailStructure(volunteer.getEmailAddress());
            Volunteer newVolunteer = new Volunteer(volunteer.getFirstName(), volunteer.getLastName(), volunteer.getBirthday(), volunteer.getPhone(), volunteer.getEmailAddress(), volunteer.getDescription(), volunteer.getApplied_at());
            repo.save(newVolunteer);
        } catch (CompleteAllFields | EmailNotValid e) {
            setErrorMsg(e.getMessage());
        }
    }
    public void deleteVolunteer(Volunteer volunteer) {
        System.out.println(volunteer);
        try {
            System.out.println('a');
            repo.deleteById(volunteer.getId());
        } catch (Exception e) {
            throw new UserNotFound();
        }
    }

        public List<Organisation> getVolunteerOrganisations(String emailAddress) {
        List<Organisation> org = new ArrayList<>();
        List<Volunteer> vol = repo.findAll().stream().filter(volunteer -> volunteer.getEmailAddress().equals(emailAddress))
                                                     .collect(Collectors.toList());
        for(Volunteer v : vol) {
            org.add(repoOrg.findByTitle(v.getApplied_at()).get());
        }
        return org;
    }
    public void deleteVolunteerOrganisation(String emailAddress,String title) {
        List<Volunteer> vol = repo.findAll().stream().filter(volunteer -> (volunteer.getEmailAddress().equals(emailAddress) &&
                                                                          volunteer.getApplied_at().equals(title)))
                                                     .collect(Collectors.toList());
        repo.deleteById(vol.get(0).getId());
    }
}
