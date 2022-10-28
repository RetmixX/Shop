package com.retmix.shop.shop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String EMAIL_PATTER = "^[_A-Za-z0-9-\\+]+(\\.A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final Pattern pattern;
    private Matcher matcher;

    public Validation() {
        pattern = Pattern.compile(EMAIL_PATTER);
    }

    public boolean validationEmail(String email){
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validationPassword(String password){
        return password.length()>=6;
    }
}
