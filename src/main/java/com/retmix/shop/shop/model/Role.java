package com.retmix.shop.shop.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    CLIENT(Set.of(Permission.ADD_TO_BASKET
    ,Permission.DELETE_PRODUCT_FROM_BASKET, Permission.CHECK_SELF_BASKET)),

    ADMIN(Set.of(Permission.DELETE_PRODUCT, Permission.ADD_PRODUCT, Permission.UPDATE_PRODUCT));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
    }
}
