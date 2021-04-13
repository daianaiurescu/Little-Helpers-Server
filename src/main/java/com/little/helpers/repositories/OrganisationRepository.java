package com.little.helpers.repositories;

import com.little.helpers.models.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {
    @Query(value = "SELECT o FROM Organisation o WHERE o.title = ?1")
    Optional<Organisation> findByTitle(String title);
}
