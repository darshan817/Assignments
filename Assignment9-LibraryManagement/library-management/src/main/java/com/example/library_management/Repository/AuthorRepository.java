package com.example.library_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library_management.Entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
