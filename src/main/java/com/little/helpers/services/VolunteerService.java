package com.little.helpers.services;

import com.little.helpers.models.Volunteer;
import com.little.helpers.repositories.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VolunteerService {
    @Autowired
    VolunteerRepository repo;

    public void newVolunteer(Volunteer volunteer) throws IOException {

        Volunteer newVolunteer = new Volunteer(volunteer.getFirstName(), volunteer.getLastName(), volunteer.getBirthday(), volunteer.getPhone(), volunteer.getEmailAddress(), volunteer.getDescription(), volunteer.getApplied_at());
        repo.save(newVolunteer);
    }
}
