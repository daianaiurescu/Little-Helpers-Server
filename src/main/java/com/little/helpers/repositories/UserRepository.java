package com.little.helpers.repositories;

import com.little.helpers.models.User;
import com.little.helpers.models.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u WHERE u.firstName = ?1")
    Optional<User> findByFirstName(String firstName);

    @Query(value = "SELECT u FROM User u WHERE u.emailAddress = ?1")
    Optional<User> findByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String emailAddress);

    boolean existsByfirstName(String firstName);

    User findByTokens(UserToken token);

}
