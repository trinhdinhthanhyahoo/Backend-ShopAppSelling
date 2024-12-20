package com.example.ShopAppSelling.Services;

import java.util.List;

import com.example.ShopAppSelling.DTO.OrderDTO;
import com.example.ShopAppSelling.Models.Order;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;

    Order getOrderById(Double id);

    List<Order> getAllOrders();

    Order updateOrder(Double orderId, OrderDTO orderDTO);

    void deleteOrder(Double id);
}
