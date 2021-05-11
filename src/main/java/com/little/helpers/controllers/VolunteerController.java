package com.little.helpers.controllers;


import com.little.helpers.models.User;
import com.little.helpers.models.Volunteer;
import com.little.helpers.repositories.VolunteerRepository;
import com.little.helpers.services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class VolunteerController {
    @Autowired
    private VolunteerRepository repo;

    @Autowired
    private VolunteerService service;

    @GetMapping("/GetVolunteers")
    public List<Volunteer> getVolunteers() {
        return repo.findAll();
    }

    @PostMapping("/SaveVolunteer")
    public ResponseEntity<String> SaveVolunteer(@RequestBody Volunteer volunteer) throws IOException {
            service.newVolunteer(volunteer);
            if (service.getErrorMsg().isEmpty()) {
                return new ResponseEntity<>("Saving complete", HttpStatus.CREATED);
            }
            else
            return new ResponseEntity<>(service.getErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
