package com.little.helpers.repositories;

import com.little.helpers.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p FROM Product p WHERE p.sold_by = ?1")
    Optional<Product> findBySold_by(String title);
    @Query(value = "SELECT p FROM Product p WHERE p.title = ?1")
    Optional<Product> findByTitle(String title);
}
