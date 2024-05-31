package com.example.OnlineStore.repo;

import com.example.OnlineStore.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
