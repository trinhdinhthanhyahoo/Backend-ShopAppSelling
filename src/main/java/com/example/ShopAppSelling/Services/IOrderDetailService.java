package com.example.ShopAppSelling.Services;

import java.util.List;

import com.example.ShopAppSelling.DTO.OrderDetailDTO;
import com.example.ShopAppSelling.Models.OrderDetail;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;

    OrderDetail getOrderDetailById(Long id) throws Exception;

    List<OrderDetail> getAllOrderDetails() throws Exception;

    OrderDetail updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO) throws Exception;

    void deleteOrderDetail(Long id) throws Exception;

    List<OrderDetail> findDetailsByOrderId(Long orderId) throws Exception;
}