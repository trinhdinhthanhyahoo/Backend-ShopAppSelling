package com.example.ShopAppSelling.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<com.example.ShopAppSelling.Models.Order, Long> {

    List<com.example.ShopAppSelling.Models.Order> findByUserId(Long userId);
}
