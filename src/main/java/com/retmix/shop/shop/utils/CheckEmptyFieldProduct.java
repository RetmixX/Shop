package com.retmix.shop.shop.utils;

import com.retmix.shop.shop.model.Products;

import java.util.HashMap;
import java.util.Map;

public class CheckEmptyFieldProduct {

    public Map<String, String> checkFields(Products products){
        Map<String, String> response = new HashMap<>();
        if (products.getTitle()==null) response.put("title","field title can not be blank");
        if (products.getDescription()==null) response.put("description","field description can not be blank");
        if (products.getPrice()==null) response.put("price","field price can not be blank");
        return response;
    }
}
