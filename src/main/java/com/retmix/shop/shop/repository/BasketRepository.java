package com.retmix.shop.shop.repository;

import com.retmix.shop.shop.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByUserId(Long id);
    List<Basket> findByUserEmail(String email);
}
