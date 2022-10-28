package com.retmix.shop.shop.service.impl;

import com.retmix.shop.shop.model.Products;
import com.retmix.shop.shop.model.User;
import com.retmix.shop.shop.repository.ProductRepository;
import com.retmix.shop.shop.repository.RoleRepository;
import com.retmix.shop.shop.repository.UserRepository;
import com.retmix.shop.shop.service.UserService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Data
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;

    @Override
    public User register(User user) {
        User registerUser = user;
        return null;

    }

    @Override
    public User auth(String email, String password) {
        return null;
    }

    @Override
    public List<Products> showProductList() {
        return productRepository.findAll();
    }

    @Override
    public Products addNewProduct(Products products) {
        return productRepository.save(products);
    }

    @Override
    public Products updateProductById(Long id, BigDecimal updatePrice) {
        Products updateProduct = productRepository.findById(id).orElse(null);
        if (updateProduct==null) throw new NullPointerException("Product with id - "+id+" not found");
        updateProduct.setPrice(updatePrice);
        return productRepository.save(updateProduct);
    }

    @Override
    public void removeProductById(Long id) {
        Products removeProduct = productRepository.findById(id).orElse(null);
        if (removeProduct==null) throw new NullPointerException("Product with id - "+id+" not found!");
        productRepository.delete(removeProduct);
    }
}
