package com.retmix.shop.shop.service;

import com.retmix.shop.shop.model.Products;
import com.retmix.shop.shop.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    User register(User user);

    User auth(String email, String password);

    List<Products> showProductList();

    Products addNewProduct(Products newProduct);

    Products updateProductById(Long id, BigDecimal updatePrice);

    void removeProductById(Long id);


}
