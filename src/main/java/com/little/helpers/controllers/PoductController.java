package com.little.helpers.controllers;

import com.little.helpers.models.AuthRequest;
import com.little.helpers.models.Product;
import com.little.helpers.models.Volunteer;
import com.little.helpers.repositories.ProductRepository;
import com.little.helpers.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<String> SaveProduct(@RequestBody Product product) throws IOException {
        service.newProduct(product);
        if (service.getErrorMsg().isEmpty()) {
            return new ResponseEntity<>("Saving complete", HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(service.getErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/DeleteProduct/{title}")
    public void DeleteProduct(@PathVariable("title") String title){
         service.deleteProduct(title);
    }
}
