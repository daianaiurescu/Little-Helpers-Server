package com.little.helpers.controllers;

import com.little.helpers.models.Order;
import com.little.helpers.repositories.OrderRepository;
import com.little.helpers.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository repo;
    @Autowired
    private OrderService service;

    @GetMapping("/Orders")
    public List<Order> getOrders() {
        return repo.findAll();
    }

    @PostMapping("/PlaceOrder")
    public ResponseEntity<String> SaveOrder(@RequestBody Order order) throws IOException {
        service.newOrder(order);

        if (service.getErrorMsg().length()==0) {
            return new ResponseEntity<>("Saving complete", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(service.getErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
