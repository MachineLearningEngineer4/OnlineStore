package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Cart;
import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.repo.CartRepository;
import com.example.OnlineStore.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddToCartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public AddToCartService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public void addToCart(Product product, int quantity, String category) {
        Cart cart = new Cart();
        cart.setProductName(product.getName());
        cart.setProductPrice(product.getPrice());
        cart.setProductQuantity(quantity);
        cart.setProductCategory(category);
        product.setCart(cart);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        cart.setProducts(productList);
        cartRepository.save(cart);
    }
}
