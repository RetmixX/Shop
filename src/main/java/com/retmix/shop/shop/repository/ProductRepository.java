package com.retmix.shop.shop.repository;

import com.retmix.shop.shop.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
    Products findByTitle(String title);
}
