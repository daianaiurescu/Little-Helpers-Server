package com.little.helpers.services;

import com.little.helpers.models.Organisation;
import com.little.helpers.models.User;
import com.little.helpers.repositories.OrganisationRepository;
import com.little.helpers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisationService {
    @Autowired
    private OrganisationRepository repo;

    public List<Organisation> listAll() {
        return repo.findAll();
    }

    public void save(Organisation std) {
        repo.save(std);
    }

    public Organisation get(int id) {
        return repo.findById(id).get();
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}
