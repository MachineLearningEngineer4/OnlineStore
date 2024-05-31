package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AddProductService {

    private final ProductRepository productRepository;

    public AddProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(@RequestParam String name, @RequestParam String description,
                           @RequestParam int price, @RequestParam String category) {
        Product product = new Product(name, description, price, category);
        productRepository.save(product);
    }
}

