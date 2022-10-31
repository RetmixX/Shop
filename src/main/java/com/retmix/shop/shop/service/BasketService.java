package com.retmix.shop.shop.service;

import com.retmix.shop.shop.model.Basket;

import java.util.List;

public interface BasketService {

    List<Basket> getBasket(Long idUser);
    void addProductInBasket(Long idUser, Long idProduct);
    void deleteProductFromBasket(Basket basket);

}
