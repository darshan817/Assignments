package com.example.ecomm_backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
// @SuppressWarnings("null")
public class ProductDTO {

    private Long id;

    @NotBlank(message = "product title cannot be empty")
    private String title; 

    @NotBlank(message = "product description cannot be empty")
    private String description; 

    @NotNull(message = "product price cannot be null") 
    private Double price; 

    @NotBlank(message = "product category cannot be empty")
    private String category; 

    private String image; 

    private RatingDTO rating; 

    // Backward compatibility getters/setters
    public String getName() {
        return this.title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getImageUrl() {
        return this.image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }
}