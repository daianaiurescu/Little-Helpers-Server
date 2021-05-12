package com.little.helpers.controllers;


import com.little.helpers.models.Organisation;
import com.little.helpers.models.User;
import com.little.helpers.models.UserPublic;
import com.little.helpers.models.Volunteer;
import com.little.helpers.repositories.VolunteerRepository;
import com.little.helpers.services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            if (service.getErrorMsg().length()==0) {
                return new ResponseEntity<>("Saving complete", HttpStatus.CREATED);
            }
            else
            return new ResponseEntity<>(service.getErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/DeleteOrganisation/{emailAddress}/{title}")
    public void deleteUserOrganisation(@PathVariable("emailAddress") String emailAddress , @PathVariable("title") String title) {
        service.deleteVolunteerOrganisation(emailAddress,title);
    }
    @GetMapping("/UserOrganisations/{emailAddress}")
    public List<Organisation> getUserOrganisations(@PathVariable("emailAddress") String emailAddress) {
        return service.getVolunteerOrganisations(emailAddress);
    }
}
