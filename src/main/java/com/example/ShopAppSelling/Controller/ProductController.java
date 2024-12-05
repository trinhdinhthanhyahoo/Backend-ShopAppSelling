package com.example.ShopAppSelling.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.example.ShopAppSelling.DTO.ProductDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @GetMapping("") // http://localhost:8088/products?page=1&limit=10
    public ResponseEntity<String> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        return ResponseEntity.ok(String.format("getAllProducts Page: %d, Limit: %d", page, limit));
    }

    private String storeFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IOException("Failed to get original filename");
        }

        String fileName = StringUtils.cleanPath(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        Path uploadDir = Paths.get("uploads");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    // {
    // "name":"Iphone 16 PRM",
    // "price": 3500000,
    // "thumbnail": "",
    // "description":"This is a new smartphone",
    // "category_id": 1
    // }
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // http://localhost:8088/products
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            List<MultipartFile> files = productDTO.getFiles();
            List<String> thumbnails = new ArrayList<>();

            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    if (file.getSize() > 10 * 1024 * 1024) {
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .body("File size must be less than 10MB");
                    }
                    String contentType = file.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .body("File must be an image");
                    }
                    String fileName = storeFile(file);
                    String fileUrl = "/uploads/" + fileName;
                    thumbnails.add(fileUrl);
                }
                productDTO.setThumbnail(thumbnails);
            }

            return ResponseEntity.ok(String.format("createProduct: %s", productDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating product: " + e.getMessage());
        }
    }

    @PutMapping("/{id}") // http://localhost:8088/products/1
    public ResponseEntity<String> updateProduct(@PathVariable Long id) {
        return ResponseEntity.ok("Product updated");
    }

    @DeleteMapping("/{id}") // http://localhost:8088/products/1
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok("Product deleted");
    }

    @GetMapping("/{id}") // http://localhost:8088/products/1
    public ResponseEntity<String> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok("Product with id " + id);
    }
}
