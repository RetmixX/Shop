package com.retmix.shop.shop.controller;

import com.retmix.shop.shop.response.errors.EmptyProductField;
import com.retmix.shop.shop.response.errors.ErrorAccessAndEmpty;
import com.retmix.shop.shop.response.messages.AddObject;
import com.retmix.shop.shop.response.messages.Message;
import com.retmix.shop.shop.model.Products;
import com.retmix.shop.shop.service.UserService;
import com.retmix.shop.shop.utils.CheckEmptyFieldProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class ProductController {

    private final UserService userService;

    @Autowired
    public ProductController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("products")
    public ResponseEntity<?> showAllProduct(){
        Map<String, List<Products>> response = new HashMap<>();
        List<Products> products = userService.showProductList();
        if (products==null || products.isEmpty()){
            ErrorAccessAndEmpty error = new ErrorAccessAndEmpty(404, "Not found");
            Map<String, ErrorAccessAndEmpty> errorResponse = new HashMap<>();
            errorResponse.put("error", error);
            return ResponseEntity.status(404).body(errorResponse);
        }
        response.put("data", products);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<?> removeProductById(@PathVariable Long id){
        try{
            userService.removeProductById(id);
            Map<String, Message> response = new HashMap<>();
            response.put("data", new Message("Product removed"));
            return ResponseEntity.ok().body(response);

        }catch (NullPointerException nullPointerException){
            HashMap<String, ErrorAccessAndEmpty> errorResponse = new HashMap<>();
            errorResponse.put("error", new ErrorAccessAndEmpty(404, "Not found"));
            return ResponseEntity.status(404).body(errorResponse);
        }
    }

    @PostMapping("product")
    public ResponseEntity<?> addNewProduct(@RequestBody Products products){
        Map<String, String> errorResponse = new CheckEmptyFieldProduct().checkFields(products);
        if (!errorResponse.isEmpty()){
            HashMap<String, EmptyProductField> response = new HashMap<>();
            response.put("error", new EmptyProductField(422, "Validation error", errorResponse));
            return ResponseEntity.status(422).body(response);
        }

        Products newProduct = userService.addNewProduct(products);
        Map<String, AddObject> response = new HashMap<>();
        response.put("data", new AddObject(newProduct.getId(), "Product added"));
        return ResponseEntity.status(201).body(response);
    }
}
