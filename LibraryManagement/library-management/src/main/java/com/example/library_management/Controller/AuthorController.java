package com.example.library_management.Controller;

import com.example.library_management.DTO.AuthorRequestDTO;
import com.example.library_management.DTO.AuthorResponseDTO;
import com.example.library_management.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public AuthorResponseDTO addAuthor(@RequestBody AuthorRequestDTO author) {
        return authorService.createAuthor(author);
    }

    @GetMapping
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorResponseDTO getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PatchMapping("/{id}")
    public AuthorResponseDTO updateAuthor(@PathVariable Long id, @RequestBody AuthorRequestDTO author) {
    return authorService.updateAuthor(id, author);
}


    @DeleteMapping("/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

}
