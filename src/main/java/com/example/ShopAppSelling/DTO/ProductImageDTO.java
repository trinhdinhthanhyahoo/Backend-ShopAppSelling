package com.example.ShopAppSelling.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImageDTO {

    @JsonProperty("product_id")
    private Long productId;

    @Size(min = 5, max = 500, message = "At least 5 image is required")
    @JsonProperty("image_url")
    private String imageUrl;
}
