package com.retmix.shop.shop.controller;

import com.retmix.shop.shop.dto.BasketDTO;
import com.retmix.shop.shop.jwt.JWTProvider;
import com.retmix.shop.shop.model.Basket;
import com.retmix.shop.shop.model.Products;
import com.retmix.shop.shop.model.User;
import com.retmix.shop.shop.response.errors.ErrorAccessAndEmpty;
import com.retmix.shop.shop.service.BasketService;
import com.retmix.shop.shop.service.ProductService;
import com.retmix.shop.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class BasketController {

    private final UserService userService;
    private final JWTProvider jwtProvider;
    private final BasketService basketService;

    private final ProductService productService;

    @Autowired
    public BasketController(UserService userService, JWTProvider jwtProvider,
                            BasketService basketService, ProductService productService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.basketService = basketService;
        this.productService = productService;
    }
    @PreAuthorize("hasAuthority('checkSelfBasket')")
    @GetMapping("cart")
    public ResponseEntity<?> showMyBasket(@RequestHeader Map<String, String> headers) {
        User user = getUser(headers);
        List<BasketDTO> basketDTOS = new ArrayList<>();

        for (Basket item : basketService.getBasket(user.getId())) {
            basketDTOS.add(new BasketDTO().toBasketDTO(item));
        }

        if (basketDTOS.isEmpty()) {
            ErrorAccessAndEmpty error = new ErrorAccessAndEmpty(404, "Not found");
            Map<String, ErrorAccessAndEmpty> errorResponse = new HashMap<>();
            errorResponse.put("error", error);
            return ResponseEntity.status(404).body(errorResponse);
        }
        Map<String, List<BasketDTO>> response = new HashMap<>();
        response.put("data", basketDTOS);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('addToBasket')")
    @PostMapping("cart/{product_id}")
    public ResponseEntity<?> addProductToBasket(@RequestHeader Map<String, String> headers, @PathVariable Long product_id){

        User user = getUser(headers);
        Products products = productService.findById(product_id);
        if (products==null) return notFound();

        basketService.addProductInBasket(user.getId(), products.getId());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product add to card");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('deleteFromBasket')")
    @DeleteMapping("cart/{id}")
    public ResponseEntity<?> removedFromBasket(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        User user = getUser(headers);
        Basket basket =  basketService.getBasket(user.getId()).get(0);
        if (basket==null) return notFound();

        basketService.deleteProductFromBasket(basket);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Item removed from cart");
        return ResponseEntity.ok(response);
    }



    private User getUser(Map<String, String> headers){
        String token = headers.get("authorization");
        return userService.findByEmail(jwtProvider.getEmail(token));
    }

    private ResponseEntity<?> notFound(){
        Map<String, ErrorAccessAndEmpty> response = new HashMap<>();
        response.put("error", new ErrorAccessAndEmpty(404, "Not found"));
        return ResponseEntity.status(404).body(response);
    }


}
