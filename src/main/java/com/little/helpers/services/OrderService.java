package com.little.helpers.services;

import com.little.helpers.models.Order;
import com.little.helpers.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrderService {
    @Autowired
    OrderRepository repo;
    private String errorMsg;
    public String getErrorMsg() {

        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {

        this.errorMsg = errorMsg;
    }

    public void newOrder(Order order) throws IOException {
        try {
            errorMsg = "";
            Order newOrder = new Order(order.getFirstName(), order.getLastName(), order.getEmail(), order.getPhone(), order.getAddress(), order.getTotal());
            newOrder.setProducts(order.getProducts());
            repo.save(newOrder);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }

    }
}
