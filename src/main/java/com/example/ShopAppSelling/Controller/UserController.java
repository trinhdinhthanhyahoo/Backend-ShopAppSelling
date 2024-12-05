package com.example.ShopAppSelling.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ShopAppSelling.DTO.UserDTO;
import com.example.ShopAppSelling.DTO.UserLoginDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/users") // http://localhost:8088/users
public class UserController {
    @PostMapping("/register") // http://localhost:8088/users/register
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.ok(String.format("Register successfully: %s", userDTO));
            } else {
                return ResponseEntity.badRequest().body("Password and retype password do not match");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(("Register failed"));
        }
    }

    @PostMapping("/login") // http://localhost:8088/users/login
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok("token");
    }

}
