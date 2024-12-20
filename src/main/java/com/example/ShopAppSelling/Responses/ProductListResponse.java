package com.example.ShopAppSelling.Responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {

    private List<ProductResponse> products;
    private int totalPages;
    private long totalProducts;

}
