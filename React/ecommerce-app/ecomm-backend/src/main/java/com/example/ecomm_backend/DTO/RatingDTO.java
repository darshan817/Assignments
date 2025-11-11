package com.example.ecomm_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RatingDTO {
    private Double rate;
    private Integer count;
}