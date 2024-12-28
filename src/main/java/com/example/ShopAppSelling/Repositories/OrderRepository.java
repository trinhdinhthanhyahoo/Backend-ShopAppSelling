package com.example.ShopAppSelling.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ShopAppSelling.Models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
