package com.retmix.shop.shop.controller;

import com.retmix.shop.shop.response.errors.EmptyObjectField;
import com.retmix.shop.shop.response.errors.ErrorAccessAndEmpty;
import com.retmix.shop.shop.response.messages.AddObject;
import com.retmix.shop.shop.response.messages.Message;
import com.retmix.shop.shop.model.Products;
import com.retmix.shop.shop.service.UserService;
import com.retmix.shop.shop.utils.CheckEmptyObjectProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('deleteProduct')")
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

    @PreAuthorize("hasAuthority('addProduct')")
    @PostMapping("product")
    public ResponseEntity<?> addNewProduct(@RequestBody Products products){
        Map<String, String> errorResponse = new CheckEmptyObjectProduct().checkFieldsProduct(products);
        if (!errorResponse.isEmpty()){
            HashMap<String, EmptyObjectField> response = new HashMap<>();
            response.put("error", new EmptyObjectField(422, "Validation error", "One of the fields is empty"));
            return ResponseEntity.status(422).body(response);
        }

        Products newProduct = userService.addNewProduct(products);
        Map<String, AddObject> response = new HashMap<>();
        response.put("data", new AddObject(newProduct.getId(), "Product added"));
        return ResponseEntity.status(201).body(response);
    }

    @PreAuthorize("hasAuthority('updateProduct')")
    @PatchMapping("product/{id}")
    public ResponseEntity<?> updateCostProduct(@PathVariable Long id, @RequestBody Products products){
        try{
            if (products==null || products.getPrice()==null){
                Map<String, EmptyObjectField> errorResponse = new HashMap<>();
                errorResponse.put("error", new EmptyObjectField(422, "Validation failed", "Field price is empty"));
                return ResponseEntity.status(422).body(errorResponse);
            }
            Products updateProduct = userService.updateProductById(id, products.getPrice());
            Map<String, Products> response = new HashMap<>();
            response.put("data", updateProduct);
            return ResponseEntity.ok().body(response);
        }catch (Exception exception){
            Map<String, ErrorAccessAndEmpty> errorResponse = new HashMap<>();
            errorResponse.put("error", new ErrorAccessAndEmpty(404, "Not found"));
            return ResponseEntity.status(404).body(errorResponse);
        }
    }
}
