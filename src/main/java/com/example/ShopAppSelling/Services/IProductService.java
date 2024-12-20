package com.example.ShopAppSelling.Services;

import com.example.ShopAppSelling.DTO.ProductDTO;
import com.example.ShopAppSelling.DTO.ProductImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.example.ShopAppSelling.Models.*;
import com.example.ShopAppSelling.Responses.ProductResponse;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;

    Product getProductById(Double id) throws Exception;

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    Product updateProduct(Double id, ProductDTO productDTO) throws Exception;

    void deleteProduct(Double id);

    boolean existsByName(String name);

    ProductImage createProductImage(Double productId, ProductImageDTO productImageDTO) throws Exception;

}
