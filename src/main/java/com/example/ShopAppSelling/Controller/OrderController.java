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
import com.example.ShopAppSelling.Models.Order;
import com.example.ShopAppSelling.Services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @GetMapping("") // http://localhost:8088/orders?page=1&limit=10
    public ResponseEntity<String> getAllOrders(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        return ResponseEntity.ok(String.format("getAllOrders Page: %d, Limit: %d", page, limit));
    }

    @PostMapping("") // http://localhost:8088/orders
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}") // http://localhost:8088/orders/1
    public ResponseEntity<?> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("Order updated");
    }

    @DeleteMapping("/{id}") // http://localhost:8088/orders/1
    public ResponseEntity<String> deleteOrder(@PathVariable Double id) {
        return ResponseEntity.status(HttpStatus.OK).body("Order is deleted successfully");
    }

    @GetMapping("/user/{user_id}") // http://localhost:8088/orders/user/1
    public ResponseEntity<String> getOrderByUserId(@PathVariable Double user_id) {
        return ResponseEntity.ok("Order with user id " + user_id);
    }
}
