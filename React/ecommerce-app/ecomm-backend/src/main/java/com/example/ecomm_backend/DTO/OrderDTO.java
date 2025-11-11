package com.example.ecomm_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String addressLine1;
    private String state;
    private String pincode;

    // For saving new orders
    private List<CartItemDto> cartItems; // ✅ Add this

    // For returning order details
    private List<OrderItemDTO> orderItems; 

    private Double originalPrice;
    private String promoCode;
    private Double discountAmount;
    private Double finalPrice;
    private String status;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    // Display fields
    private String timeSinceOrder;
    private String timeRemainingToCancel;
    private String timeRemainingToDeliver;
}
