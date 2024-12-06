package com.example.ShopAppSelling.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

@Entity
@Data
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private com.example.ShopAppSelling.Models.Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private com.example.ShopAppSelling.Models.Product product;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "number_of_products", nullable = false)
    private int numberOfProducts;

    @Column(name = "total_money", nullable = false)
    private float totalMoney;

    @Column(name = "color")
    private String color;

}
