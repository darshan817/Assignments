package com.example.ecomm_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDto {
    private Long productId;
    private Integer quantity;
    private Double price;
}
