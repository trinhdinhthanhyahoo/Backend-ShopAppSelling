package com.example.ShopAppSelling.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ShopAppSelling.DTO.OrderDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    @GetMapping("") // http://localhost:8088/orders?page=1&limit=10
    public ResponseEntity<String> getAllOrders(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        return ResponseEntity.ok(String.format("getAllOrders Page: %d, Limit: %d", page, limit));
    }

    @PostMapping("") // http://localhost:8088/orders
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("createOrder: %s", orderDTO));
    }

    @PutMapping("/{id}") // http://localhost:8088/orders/1
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("Order updated");
    }

    @DeleteMapping("/{id}") // http://localhost:8088/orders/1
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Order is deleted successfully");
    }

    @GetMapping("/user/{user_id}") // http://localhost:8088/orders/user/1
    public ResponseEntity<String> getOrderByUserId(@PathVariable Long user_id) {
        return ResponseEntity.ok("Order with user id " + user_id);
    }
}
