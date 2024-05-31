package com.example.OnlineStore.repo;

import com.example.OnlineStore.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " + "CONCAT (p.name, p.price, p.category)" + "LIKE %?1%")
    public Page<Product> findAll(String keyword, Pageable pageable);
}
