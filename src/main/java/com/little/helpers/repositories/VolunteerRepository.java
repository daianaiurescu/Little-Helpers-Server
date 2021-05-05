package com.little.helpers.repositories;

import com.little.helpers.models.Organisation;
import com.little.helpers.models.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
    @Query(value = "SELECT v FROM Volunteer v WHERE v.applied_at = ?1")
    Optional<Organisation> findByApplied_at(String title);
}
