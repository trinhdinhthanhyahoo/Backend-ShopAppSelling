package com.example.ShopAppSelling.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ShopAppSelling.DTO.OrderDetailDTO;
import com.example.ShopAppSelling.Models.OrderDetail;
import com.example.ShopAppSelling.Responses.OrderDetailResponse;
import com.example.ShopAppSelling.Services.IOrderDetailService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/order-details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final IOrderDetailService orderDetailService;

    @PostMapping("") // http://localhost:8088/api/v1/order-details
    public ResponseEntity<OrderDetailResponse> createOrderDetail(@RequestBody @Valid OrderDetailDTO orderDetailDTO)
            throws Exception {
        try {
            OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(OrderDetailResponse.fromOrderDetail(orderDetail));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/{id}") // http://localhost:8088/api/v1/order-details/1
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable("id") Long id) throws Exception {
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
            return ResponseEntity.ok(orderDetail);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}") // http://localhost:8088/api/v1/order-details/order/1
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetailsByOrderId(@PathVariable("orderId") Long orderId)
            throws Exception {
        try {
            List<OrderDetail> orderDetails = orderDetailService.findDetailsByOrderId(orderId);
            List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                    .map(OrderDetailResponse::fromOrderDetail)
                    .toList();

            return ResponseEntity.ok(orderDetailResponses);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/{id}") // http://localhost:8088/api/v1/order-details/1
    public ResponseEntity<?> updateOrderDetail(@PathVariable("id") Long id,
            @RequestBody @Valid OrderDetailDTO orderDetailDTO) throws Exception {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok().body(orderDetail);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/{id}") // http://localhost:8088/api/v1/order-details/1
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") Long id) throws Exception {

        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body("Order detail is deleted successfully");
    }
}
