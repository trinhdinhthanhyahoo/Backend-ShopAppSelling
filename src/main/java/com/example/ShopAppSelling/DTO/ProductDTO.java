package com.example.ShopAppSelling.DTO;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 350, message = "Product name must be between 3 and 350 characters")
    private String name;

    @Min(value = 0, message = "Product price must be greater than 0")
    @Max(value = 100000000, message = "Product price must be less than 100,000,000")
    @NotNull(message = "Product price is required")
    private Float price;

    private String thumbnail;

    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

    private MultipartFile file;
}
