package com.little.helpers.repositories;

import com.little.helpers.models.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserTokenRepository extends JpaRepository<UserToken,Integer> {
    UserToken findByToken(String t);

    @Transactional
    @Modifying
    void deleteById(int id);
    boolean existsByToken(String token);
}
