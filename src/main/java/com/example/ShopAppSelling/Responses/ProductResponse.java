package com.example.ShopAppSelling.Responses;

import com.example.ShopAppSelling.Models.BaseEntity;
import com.example.ShopAppSelling.Models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductResponse extends BaseEntity {

    private String name;

    private Float price;

    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

    private String thumbnail;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .thumbnail(product.getThumbnail())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
