package com.retmix.shop.shop.service.impl;

import com.retmix.shop.shop.model.Products;
import com.retmix.shop.shop.repository.ProductRepository;
import com.retmix.shop.shop.service.ProductService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ProductImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Products findById(Long id) {
        Products products = productRepository.findById(id).orElse(null);
        return products!=null?(products):(null);
    }
}
