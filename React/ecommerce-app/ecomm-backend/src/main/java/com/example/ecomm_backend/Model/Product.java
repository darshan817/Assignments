package com.example.ecomm_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "products") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Product {

    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    private Long id; 

    @Column(nullable = false, length = 500) 
    private String title; 

    @Column(length = 1000) 
    private String description; 

    @Column(nullable = false)
    private Double price; 

    @Column(nullable = false)
    private String category; 

    @Column(length = 1000)
    private String image; 

    @Column(name = "rating_rate")
    private Double ratingRate;

    @Column(name = "rating_count")
    private Integer ratingCount;

    // Backward compatibility - keep name field for existing code
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