package com.retmix.shop.shop.utils;

import com.retmix.shop.shop.dto.AuthUserDTO;
import com.retmix.shop.shop.dto.SignupUserDto;
import com.retmix.shop.shop.model.Products;

import java.util.HashMap;
import java.util.Map;

public class CheckEmptyObjectProduct {

    public Map<String, String> checkFieldsProduct(Products products){
        Map<String, String> response = new HashMap<>();
        if (products.getTitle()==null) response.put("title","field title can not be blank");
        if (products.getDescription()==null) response.put("description","field description can not be blank");
        if (products.getPrice()==null) response.put("price","field price can not be blank");
        return response;
    }

    public boolean checkFieldsSignUpUser(SignupUserDto signupUserDto){
        return (fieldEmptyOrNull(signupUserDto.getSurname())) || (fieldEmptyOrNull(signupUserDto.getName())) ||
                (fieldEmptyOrNull(signupUserDto.getLastname())) || (fieldEmptyOrNull(signupUserDto.getEmail())) ||
                (fieldEmptyOrNull(signupUserDto.getPassword()));
    }

    public boolean checkFieldLoginUser(AuthUserDTO authUserDTO){
        return fieldEmptyOrNull(authUserDTO.getEmail()) || fieldEmptyOrNull(authUserDTO.getPassword());
    }

    private boolean fieldEmptyOrNull(String field){
        return field==null || field.isEmpty();
    }
}
