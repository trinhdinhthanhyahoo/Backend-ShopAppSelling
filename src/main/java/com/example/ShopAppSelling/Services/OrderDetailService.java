package com.example.ShopAppSelling.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.ShopAppSelling.DTO.OrderDetailDTO;
import com.example.ShopAppSelling.Models.Order;
import com.example.ShopAppSelling.Models.OrderDetail;
import com.example.ShopAppSelling.Models.Product;
import com.example.ShopAppSelling.Repositories.OrderDetailRepository;
import com.example.ShopAppSelling.Repositories.OrderRepository;
import com.example.ShopAppSelling.Repositories.ProductRepository;
import com.example.ShopAppSelling.exceptions.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(product.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .color(orderDetailDTO.getColor())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetailById(Long id) throws Exception {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found with id: " + id));
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() throws Exception {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO) throws Exception {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found with id: " + orderDetailId));

        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(
                        () -> new DataNotFoundException("Order not found with id: " + orderDetailDTO.getOrderId()));

        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(
                        () -> new DataNotFoundException("Product not found with id: " + orderDetailDTO.getProductId()));

        existingOrderDetail.setOrder(order);
        existingOrderDetail.setProduct(product);
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) throws Exception {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findDetailsByOrderId(Long orderId) throws Exception {
        return orderDetailRepository.findDetailsByOrderId(orderId);
    }

}
