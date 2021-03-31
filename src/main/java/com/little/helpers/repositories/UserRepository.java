package com.little.helpers.repositories;

import com.little.helpers.models.Users;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <Users, String > {
    public Users findByEmail(String email);
}
