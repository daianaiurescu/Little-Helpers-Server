package com.little.helpers.services;

import com.little.helpers.models.Product;
import com.little.helpers.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductService {
    @Autowired
    ProductRepository repo;

    public void newProduct(Product product) throws IOException {

        Product newProduct = new Product(product.getTitle(), product.getCategory(), product.getSold_by(), product.getPrice(), product.getStock(), product.getDescription(), product.getPhoto());
        repo.save(newProduct);
    }
}
