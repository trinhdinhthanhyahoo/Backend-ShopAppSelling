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
import com.example.ShopAppSelling.Models.Category;
import com.example.ShopAppSelling.Services.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoryService categoryService;

    @PostMapping("") // http://localhost:8088/categories
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoriesDTO categoriesDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Category createdCategory = categoryService.createCategory(categoriesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("") // http://localhost:8088/categories?page=1&limit=10
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}") // http://localhost:8088/categories/1
    public ResponseEntity<?> updateCategory(@PathVariable Double id,
            @Valid @RequestBody CategoriesDTO categoriesDTO) {
        Category updatedCategory = categoryService.updateCategory(id, categoriesDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}") // http://localhost:8088/categories/1
    public ResponseEntity<String> deleteCategory(@PathVariable Double id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body("Category is deleted successfully");
    }

    @GetMapping("/{id}") // http://localhost:8088/categories/1
    public ResponseEntity<Category> getCategoryById(@PathVariable Double id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
}
