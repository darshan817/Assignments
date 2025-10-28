package com.example.library_management.Entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int stock;
    
     @ManyToOne
     @JoinColumn(name = "author_id")
     private Author author;

     @ManyToMany(mappedBy = "borrowedBooks")
     @JsonIgnore
     private Set<Member> members = new HashSet<>();
}
