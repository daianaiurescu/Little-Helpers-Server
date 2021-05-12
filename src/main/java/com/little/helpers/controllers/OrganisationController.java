package com.little.helpers.controllers;


import com.little.helpers.models.Organisation;
import com.little.helpers.repositories.OrganisationRepository;
import com.little.helpers.services.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrganisationController {
    @Autowired
    private OrganisationRepository repo;
    @Autowired
    private OrganisationService service;
    @GetMapping("/Organisations")
    public List<Organisation> getOrganisations() {
        return repo.findAll();
    }

    @GetMapping("/Organisations/{title}")
    public Organisation getOrganisation(@PathVariable("title") String title) {
        return repo.findByTitle(title).orElseThrow(() -> {
            return new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Organisation with title: %s not found", title));
        });
    }
    @PostMapping("/ChangeOrganisationDetails")
    public void changeOrganisationDetails(@RequestBody Organisation organisation) {
        service.changeOrgDetails(organisation.getTitle(), organisation.getDescription());
    }
}
