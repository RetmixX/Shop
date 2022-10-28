package com.retmix.shop.shop.response.errors;

import java.util.Map;

public class EmptyProductField extends ErrorAccessAndEmpty{
    String responseError;

    public EmptyProductField(int code, String message, Map<String, String> responseError) {
        super(code, message);
        this.responseError = responseError.toString();
    }
}
