package com.example.ShopAppSelling.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.ShopAppSelling.DTO.OrderDTO;
import com.example.ShopAppSelling.Models.Order;
import com.example.ShopAppSelling.Models.OrderStatus;
import com.example.ShopAppSelling.Models.User;
import com.example.ShopAppSelling.Repositories.OrderRepository;
import com.example.ShopAppSelling.Repositories.UserRepository;
import com.example.ShopAppSelling.exceptions.DataNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        try {
            Long userId = orderDTO.getUserId();
            User user = userRepository
                    .findById(userId)
                    .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + userId));

            Order order = new Order();
            modelMapper.map(orderDTO, order);
            order.setUser(user);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(OrderStatus.PENDING.toString());

            // Kiểm tra shipping date
            LocalDate shippingDate = orderDTO.getShippingDate() == null
                    ? LocalDate.now()
                    : orderDTO.getShippingDate();
            if (shippingDate.isBefore(LocalDate.now())) {
                throw new DataNotFoundException("Date must be at least today !");
            }
            order.setActive(true);
            order.setShippingDate(shippingDate.atStartOfDay());
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(order);
            System.out.println(jsonString);
            return orderRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Order getOrderById(Long id) {

        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(Long orderId, OrderDTO orderDTO) {
        Order order = getOrderById(orderId);
        modelMapper.map(orderDTO, order);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
