package com.example.ShopAppSelling.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<com.example.ShopAppSelling.Models.Product, Long> {
    boolean existsByName(String name);

    Page<com.example.ShopAppSelling.Models.Product> findByCategoryId(Long categoryId, Pageable pageable);
}
