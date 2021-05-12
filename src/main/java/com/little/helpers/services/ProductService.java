package com.little.helpers.services;

import com.little.helpers.models.Product;
import com.little.helpers.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository repo;
    private String errorMsg;
    public String getErrorMsg() {

        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {

        this.errorMsg = errorMsg;
    }

    public void newProduct(Product product) throws IOException {
        try {
            Product newProduct = new Product(product.getTitle(), product.getCategory(), product.getSold_by(), product.getPrice(), product.getStock(), product.getDescription(), product.getPhoto());
            repo.save(newProduct);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }

    }

    public void deleteProduct(String title) {
        try {
            Optional<Product> productToDelete = this.repo.findByTitle(title);
            this.repo.deleteById(productToDelete.get().getId());
        }catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }
}
