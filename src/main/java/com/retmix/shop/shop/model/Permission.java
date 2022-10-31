package com.retmix.shop.shop.model;

public enum Permission {
    ADD_PRODUCT("addProduct"),
    UPDATE_PRODUCT("updateProduct"),
    DELETE_PRODUCT("deleteProduct"),
    SIMPLE_USER("simpleUser"),
    ADD_TO_BASKET("addToBasket"),
    DELETE_PRODUCT_FROM_BASKET("deleteFromBasket"),
    CHECK_SELF_BASKET("checkSelfBasket");


    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
