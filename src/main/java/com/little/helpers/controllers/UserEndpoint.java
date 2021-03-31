package com.little.helpers.controllers;

import com.little.helpers.models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/template")
public class UserEndpoint {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    private final MongoTemplate mongoTemplate;
//
//    public UserEndpoint(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//    }
//
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public List <Users> getAllUsers() {
//        logger.info("Getting all Users.");
//        return mongoTemplate.findAll(Users.class);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Users getEmployee(@PathVariable long id) {
//        logger.info("Getting Employee with ID: {}.", id);
//        Users userModel = mongoTemplate.findById(id, Users.class);
//        return userModel;
//    }
//
//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public Users add(@RequestBody Users userModel) {
//        logger.info("Saving Employee.");
//        return mongoTemplate.save(userModel);
//    }
}
