package com.retmix.shop.shop.dto;

import com.retmix.shop.shop.model.Basket;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class BasketDTO {
    private Long id;
    private Long product_id;
    private String name;
    private String description;
    private BigDecimal price;

    public BasketDTO() {
    }

    public BasketDTO toBasketDTO(Basket basket){
        return new BasketDTO(
                basket.getId(),
                basket.getProducts().getId(),
                basket.getProducts().getTitle(),
                basket.getProducts().getDescription(),
                basket.getProducts().getPrice()
        );
    }
}
