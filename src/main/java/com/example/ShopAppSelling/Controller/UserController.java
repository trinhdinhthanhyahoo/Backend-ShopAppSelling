package com.example.ShopAppSelling.Controller;

import java.util.List;

import com.example.ShopAppSelling.Models.User;
import com.example.ShopAppSelling.Services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ShopAppSelling.DTO.UserDTO;
import com.example.ShopAppSelling.DTO.UserLoginDTO;

@RestController
@RequestMapping("${api.prefix}/users") // http://${api.prefix}/users
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/register") // http://${api.prefix}/users/register
    // can we register an "admin" user ?
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password does not match");
            }
            User user = userService.createUser(userDTO);
            // return ResponseEntity.ok("Register successfully");
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login") // http://${api.prefix}/users/login
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) throws Exception {
        try {
            String token = userService.login(userLoginDTO);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
