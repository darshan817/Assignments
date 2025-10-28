package com.example.library_management.Entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


     @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
     @JsonIgnoreProperties("members")
     @JoinTable(
             name = "member_books", 
             joinColumns = @JoinColumn(name = "member_id"), 
             inverseJoinColumns = @JoinColumn(name = "book_id") 
     )
     private Set<Book> borrowedBooks = new HashSet<>();
}
