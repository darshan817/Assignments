package com.example.library_management.Service;

import com.example.library_management.DTO.AuthorRequestDTO;
import com.example.library_management.DTO.AuthorResponseDTO;
import com.example.library_management.Entity.Author;
import com.example.library_management.Entity.Book;
import com.example.library_management.Repository.AuthorRepository;
import com.example.library_management.Repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {


    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public AuthorResponseDTO createAuthor(AuthorRequestDTO authorRequestDTO) {
        Author author = new Author();
        author.setName(authorRequestDTO.getName());

        if (authorRequestDTO.getBookIds() != null) {
            List<Book> books = bookRepository.findAllById(authorRequestDTO.getBookIds());
            author.setBooks(books);
        }
        Author saved = authorRepository.saveAndFlush(author);
        List<String> booksTitles = saved.getBooks().stream().map(Book::getTitle).collect(Collectors.toList());
        return new AuthorResponseDTO(saved.getId(), saved.getName(), booksTitles);
    }

    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> getAllAuthors(){
        List<Author> authors = authorRepository.findAll();

        return authors.stream().map(author -> {
            List<String> booksTitles = author.getBooks().stream().map(Book::getTitle).collect(Collectors.toList());
            return new AuthorResponseDTO(author.getId(), author.getName(), booksTitles);
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuthorResponseDTO getAuthorById(Long id){
        Author author = authorRepository.findById(id).orElseThrow(()-> new RuntimeException("Author not found with id: "+id));

        List<String> bookTitles = author.getBooks().stream().map(Book::getTitle).collect(Collectors.toList());
        return new AuthorResponseDTO(author.getId(), author.getName(), bookTitles);
    }

    @Transactional
    public AuthorResponseDTO updateAuthor(Long id,AuthorRequestDTO authorRequestDTO){
        Author author = authorRepository.findById(id).orElseThrow(()->new RuntimeException("Author not found with id: "+id));

        author.setName(authorRequestDTO.getName());
        if(authorRequestDTO.getBookIds() != null){
            List<Book> books = bookRepository.findAllById(authorRequestDTO.getBookIds());
            author.setBooks(books);
        }

        Author updated = authorRepository.saveAndFlush(author);

        List<String> bookTitles = updated.getBooks().stream().map(Book::getTitle).collect(Collectors.toList());
        return new AuthorResponseDTO(author.getId(), author.getName(), bookTitles);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        
        if(!author.getBooks().isEmpty()) {
            throw new RuntimeException("Cannot delete author: Books are still assigned!");
        }

        authorRepository.delete(author);
    }
}