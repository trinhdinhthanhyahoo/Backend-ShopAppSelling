package com.example.ShopAppSelling.DTO;

import lombok.Data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @JsonProperty("user_id")
    @NotNull(message = "User ID is required")
    private Long userId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Min(value = 10, message = "Phone number must be at least 10 digits")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("note")
    private String note;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    @JsonProperty("tracking_number")
    private String trackingNumber;

}
