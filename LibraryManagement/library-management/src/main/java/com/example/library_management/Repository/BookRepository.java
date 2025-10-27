package com.example.library_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.library_management.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{

}
    