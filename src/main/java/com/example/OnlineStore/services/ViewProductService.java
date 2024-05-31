package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ViewProductService {

    private final ProductRepository productRepository;

    public ViewProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void viewProduct(Integer id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res :: add);
        model.addAttribute("product", res);
    }
}
