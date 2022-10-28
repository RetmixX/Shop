package com.retmix.shop.shop.response.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorAccessAndEmpty {
    private int code;
    private String message;
}
