package com.example.ShopAppSelling.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ShopAppSelling.Models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findDetailsByOrderId(Long orderId);
}
