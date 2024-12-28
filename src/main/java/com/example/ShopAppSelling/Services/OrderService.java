package com.example.ShopAppSelling.Services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.ShopAppSelling.DTO.OrderDTO;
import com.example.ShopAppSelling.Models.Order;
import com.example.ShopAppSelling.Models.OrderStatus;
import com.example.ShopAppSelling.Models.User;
import com.example.ShopAppSelling.Repositories.OrderRepository;
import com.example.ShopAppSelling.Repositories.UserRepository;
import com.example.ShopAppSelling.exceptions.DataNotFoundException;
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
            User user = userRepository
                    .findById(orderDTO.getUserId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));

            modelMapper.typeMap(OrderDTO.class, Order.class)
                    .addMappings(mapper -> mapper.skip(Order::setId));

            Order order = new Order();
            modelMapper.map(orderDTO, order);
            order.setUser(user);
            order.setOrderDate(new Date());// lấy thời điểm hiện tại
            order.setStatus(OrderStatus.PENDING.toString());

            LocalDate shippingDate = orderDTO.getShippingDate() == null
                    ? LocalDate.now()
                    : orderDTO.getShippingDate();
            if (shippingDate.isBefore(LocalDate.now())) {
                throw new DataNotFoundException("Date must be at least today!");
            }
            order.setShippingDate(shippingDate);
            order.setActive(true);
            orderRepository.save(order);
            return order;

        } catch (Exception e) {
            throw new Exception("Error creating order: " + e.getMessage());
        }
    }

    @Override
    public Order getOrderById(Long id) throws Exception {
        try {
            return orderRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new Exception("Error getting order by id: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getAllOrders() throws Exception {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error getting all orders: " + e.getMessage());
        }
    }

    @Override
    public Order updateOrder(Long orderId, OrderDTO orderDTO) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        User existingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            order.get().setActive(false);
            orderRepository.save(order.get());
        } else {
            throw new DataNotFoundException("Order not found");
        }

    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) throws Exception {
        try {
            return orderRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new Exception("Error getting orders by user id: " + e.getMessage());
        }
    }
}
