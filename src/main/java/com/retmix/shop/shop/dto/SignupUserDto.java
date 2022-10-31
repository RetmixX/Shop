package com.retmix.shop.shop.dto;

import com.retmix.shop.shop.model.User;
import lombok.Data;

@Data
public class SignupUserDto {
    private String surname;
    private String name;
    private String lastname;
    private String email;
    private String password;

    public User toUser(SignupUserDto signupUserDto){
        User user = new User();
        user.setEmail(signupUserDto.getEmail());
        user.setSurname(signupUserDto.getSurname());
        user.setFirstname(signupUserDto.getName());
        user.setLastname(signupUserDto.getLastname());
        user.setPassword(signupUserDto.getPassword());

        return user;
    }
}
