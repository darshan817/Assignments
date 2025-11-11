package com.example.ecomm_backend.Model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String addressLine1;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode;

    // order items
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Column(nullable = false)
    private Double originalPrice;

    @ManyToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;

    @Column
    private Double discountAmount = 0.0;

    @Column(nullable = false)
    private Double finalPrice;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column
    private LocalDateTime deliveryDate;
}
