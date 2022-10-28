package com.retmix.shop.shop.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(schema = "shop", name = "basket")
public class Basket {
    @Id
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "id_userbasket_fk"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product", foreignKey = @ForeignKey(name = "id_product_fk"))
    private Products products;

    @Column(name = "price_basket")
    private BigDecimal priceBasket;

}
