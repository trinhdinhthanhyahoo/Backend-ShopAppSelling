package com.example.ShopAppSelling.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ShopAppSelling.Models.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Double> {

    List<ProductImage> findByProductId(Double productId);
}
