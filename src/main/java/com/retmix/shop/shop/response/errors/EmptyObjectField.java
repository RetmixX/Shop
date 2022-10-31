package com.retmix.shop.shop.response.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmptyObjectField extends ErrorAccessAndEmpty{
    String responseError;

    public EmptyObjectField(int code, String message, String responseError) {
        super(code, message);
        this.responseError = responseError;
    }
}
