package com.example.ShopAppSelling.Services;

import java.util.List;

import com.example.ShopAppSelling.DTO.OrderDTO;
import com.example.ShopAppSelling.Models.Order;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;

    Order getOrderById(Long id) throws Exception;

    List<Order> getAllOrders() throws Exception;

    Order updateOrder(Long orderId, OrderDTO orderDTO) throws Exception;

    void deleteOrder(Long id) throws Exception;

    List<Order> getOrdersByUserId(Long userId) throws Exception;
}
