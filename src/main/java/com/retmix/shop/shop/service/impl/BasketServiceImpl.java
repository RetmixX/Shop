package com.retmix.shop.shop.service.impl;

import com.retmix.shop.shop.model.Basket;
import com.retmix.shop.shop.repository.BasketRepository;
import com.retmix.shop.shop.repository.ProductRepository;
import com.retmix.shop.shop.repository.UserRepository;
import com.retmix.shop.shop.service.BasketService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class BasketServiceImpl implements BasketService {

    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;


    @Override
    public List<Basket> getBasket(Long idUser) {
        List<Basket> basketsUser = basketRepository.findByUserId(idUser);
        if (basketsUser.isEmpty()) throw new RuntimeException("Basket not found");

        return basketsUser;
    }

    @Override
    public void addProductInBasket(Long idUser, Long idProduct) {
        Basket newProductInBasket = new Basket();
        newProductInBasket.setProducts(productRepository.findById(idProduct).orElseThrow());
        newProductInBasket.setUser(userRepository.findById(idUser).orElseThrow());
        basketRepository.save(newProductInBasket);
    }

    @Override
    public void deleteProductFromBasket(Basket basket) {
        basketRepository.delete(basket);
    }

}
