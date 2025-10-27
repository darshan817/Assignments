package com.example.library_management.Service;

import com.example.library_management.DTO.BookRequestDTO;
import com.example.library_management.DTO.BookResponseDTO;
import com.example.library_management.Entity.Author;
import com.example.library_management.Repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.library_management.Entity.Book;
import com.example.library_management.Entity.Member;
import com.example.library_management.Repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {

        Author author = authorRepository.findById(bookRequestDTO.getAuthorID())
                    .orElseThrow(() -> new RuntimeException("Author not found with id: " + bookRequestDTO.getAuthorID()));
        
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());
        book.setStock(bookRequestDTO.getStock());
        book.setAuthor(author);

        Book savedBook = bookRepository.saveAndFlush(book);

        return new BookResponseDTO(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getStock(),
                savedBook.getAuthor().getName()
        );
    }

    @Transactional(readOnly = true )
    public List<BookResponseDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
            .map(book -> new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getStock(),
                book.getAuthor().getName()
            ))
        .collect(Collectors.toList());
    }

    @Transactional(readOnly = true )
    public BookResponseDTO getBookByID(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found with id :" + id));
         
        return new BookResponseDTO(book.getId(), book.getTitle(), book.getStock(),  book.getAuthor().getName());
    }


    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO){
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id : " + id));
        
        book.setTitle(bookRequestDTO.getTitle());
        book.setStock(bookRequestDTO.getStock());

        if (bookRequestDTO.getAuthorID() != null){
            Author author = authorRepository.findById(bookRequestDTO.getAuthorID()).orElseThrow();
            book.setAuthor(author);
        }

        Book updaeBook = bookRepository.saveAndFlush(book);
        return new BookResponseDTO(updaeBook.getId(), updaeBook.getTitle(), updaeBook.getStock(), updaeBook.getAuthor().getName());

    }

    @Transactional
    public void deleteBook(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id : " + id));
        
        for (Member member : book.getMembers()) {
            member.getBorrowedBooks().remove(book);
        }

        bookRepository.delete(book);
    }
 
}
