package com.retmix.shop.shop.controller;

import com.retmix.shop.shop.dto.AuthUserDTO;
import com.retmix.shop.shop.dto.SignupUserDto;
import com.retmix.shop.shop.jwt.JWTProvider;
import com.retmix.shop.shop.model.User;
import com.retmix.shop.shop.response.errors.EmptyObjectField;
import com.retmix.shop.shop.response.errors.ErrorAccessAndEmpty;
import com.retmix.shop.shop.service.UserService;
import com.retmix.shop.shop.utils.CheckEmptyObjectProduct;
import com.retmix.shop.shop.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;
    private final JWTProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, JWTProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody SignupUserDto signupUserDto){
        if (new CheckEmptyObjectProduct().checkFieldsSignUpUser(signupUserDto)){
            return validationError("Validation failed", 422,"One of the fields is empty");
        }

        if (!new Validation().validationEmail(signupUserDto.getEmail()) || !new Validation().validationPassword(signupUserDto.getPassword())){
            return validationError("Validation failed", 422,"Password or email do not meet the requirements");
        }

        User user = userService.register(signupUserDto.toUser(signupUserDto));
        if (user==null){
            return validationError("Gone", 409, "User with this email exists");
        }

        String token = jwtProvider.createToken(user.getEmail(), user.getRole().name());
        return getResponseEntity(token);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthUserDTO authUserDTO){
        if (new CheckEmptyObjectProduct().checkFieldLoginUser(authUserDTO)){
            return validationError("Validation failed", 422, "Field email or password is empty");
        }

        if (!userService.auth(authUserDTO.getEmail(), authUserDTO.getPassword())){
            Map<String, ErrorAccessAndEmpty> response = new HashMap<>();
            response.put("error", new ErrorAccessAndEmpty(401, "Authentication failed"));
            return  ResponseEntity.status(401).body(response);
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUserDTO.getEmail(), authUserDTO.getPassword()));
        String token = jwtProvider.createToken(authUserDTO.getEmail(), userService.findByEmail(authUserDTO.getEmail()).getRole().name());
        return getResponseEntity(token);
    }

    private ResponseEntity<?> getResponseEntity(String token) {
        Map<String, Map<String, String>> response = new HashMap<>();
        HashMap<String, String> infoToken = new HashMap<>();
        infoToken.put("user_token", token);
        response.put("data", infoToken);
        return ResponseEntity.status(201).body(response);
    }

    private ResponseEntity<?> validationError(String message, int code, String messageError){
        Map<String, EmptyObjectField> response = new HashMap<>();
        response.put("error", new EmptyObjectField(code, message, messageError));
        return ResponseEntity.status(code).body(response);
    }
}
