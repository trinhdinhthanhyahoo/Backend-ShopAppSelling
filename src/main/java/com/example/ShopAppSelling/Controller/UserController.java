package com.example.ShopAppSelling.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ShopAppSelling.DTO.UserDTO;
import com.example.ShopAppSelling.DTO.UserLoginDTO;
import com.example.ShopAppSelling.Services.IUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/users") // http://${api.prefix}/users
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/register") // http://${api.prefix}/users/register
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                userService.createUser(userDTO);
                return ResponseEntity.ok(String.format("Register successfully: %s", userDTO));
            } else {
                return ResponseEntity.badRequest().body("Password and retype password do not match");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(("Register failed"));
        }
    }

    @PostMapping("/login") // http://${api.prefix}/users/login
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        String token = userService.login(userLoginDTO);
        return ResponseEntity.ok(token);
    }

}
