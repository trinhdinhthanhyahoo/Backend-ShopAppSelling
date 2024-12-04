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

import com.example.ShopAppSelling.DTO.CategoriesDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @GetMapping("") // http://localhost:8088/categories?page=1&limit=10
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        return ResponseEntity.ok(String.format("getAllCategories Page: %d, Limit: %d", page, limit));
    }

    @PostMapping("") // http://localhost:8088/categories
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoriesDTO categoriesDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("createCategory: %s", categoriesDTO));
    }

    @PutMapping("/{id}") // http://localhost:8088/categories/1
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Category updated");
    }

    @DeleteMapping("/{id}") // http://localhost:8088/categories/1
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Category is deleted successfully");
    }

    @GetMapping("/{id}") // http://localhost:8088/categories/1
    public ResponseEntity<String> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok("Category with id " + id);
    }
}
