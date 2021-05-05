package com.little.helpers.controllers;

import com.little.helpers.models.Product;
import com.little.helpers.repositories.ProductRepository;
import com.little.helpers.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoductController {
    @Autowired
    private ProductRepository repo;
    @Autowired
    private ProductService service;

    @GetMapping("/Products")
    public List<Product> getProducts() {
        return repo.findAll();
    }

    @PostMapping("/SaveProduct")
    public ResponseEntity<String> SaveProduct(@RequestBody Product product) {
        try {
            service.newProduct(product);
            return new ResponseEntity<>("Saving complete", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
