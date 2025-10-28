package com.example.library_management.Service;

import com.example.library_management.DTO.MemberRequestDTO;
import com.example.library_management.DTO.MemberResponseDTO;
import com.example.library_management.Entity.Book;
import com.example.library_management.Entity.Member;
import com.example.library_management.Repository.BookRepository;
import com.example.library_management.Repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    private void borrowBook(Member member, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found with ID : " + bookId));

        if (book.getStock() < 1 ){
            throw new RuntimeException("Book " + book.getTitle() + " is out of stock");
        }

        book.setStock(book.getStock() - 1);
        bookRepository.saveAndFlush(book);

        member.getBorrowedBooks().add(book);
    }

    @Transactional
    public MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO) {
    Member member = new Member();
    member.setName(memberRequestDTO.getName());
    member.setBorrowedBooks(new HashSet<>()); 

    Member savedMember = memberRepository.saveAndFlush(member); 

    if (memberRequestDTO.getBorrowedBookIds() != null && !memberRequestDTO.getBorrowedBookIds().isEmpty()) {
        for (Long bookId : memberRequestDTO.getBorrowedBookIds()) {
            borrowBook(savedMember, bookId);
        }
    }

    Set<String> borrowedBookTitles = savedMember.getBorrowedBooks()
            .stream()
            .map(Book::getTitle)
            .collect(Collectors.toSet());

    return new MemberResponseDTO(savedMember.getId(), savedMember.getName(), borrowedBookTitles);
}



    @Transactional(readOnly = true )
    public List<MemberResponseDTO> getAllMembers(){
        List<Member> members = memberRepository.findAll();

        return members.stream().map(member -> {
            Set<String> borrowedBookTitles = member.getBorrowedBooks().stream().map(Book::getTitle).collect(Collectors.toSet());
            return new MemberResponseDTO(member.getId(), member.getName(), borrowedBookTitles);
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true )
    public MemberResponseDTO getMemberById(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Memeber not found with id : " + id));

        Set<String> borrowedBookTitles = member.getBorrowedBooks().stream().map(Book::getTitle).collect(Collectors.toSet());
        return new MemberResponseDTO(member.getId(), member.getName(), borrowedBookTitles);
    }

    @Transactional
    public  MemberResponseDTO updateMember(Long id, MemberRequestDTO memberRequestDTO){
        Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found with id : " + id));
        
        member.setName(memberRequestDTO.getName());
        
        if (memberRequestDTO.getBorrowedBookIds() != null){
            for (Long boodId : memberRequestDTO.getBorrowedBookIds()){
                borrowBook(member, boodId);
            }
        }
        memberRepository.saveAndFlush(member);
        
        Set<String> borrowedBookTitles = member.getBorrowedBooks().stream().map(Book::getTitle).collect(Collectors.toSet());
        
        return new MemberResponseDTO(member.getId(), member.getName(), borrowedBookTitles);
    }


    @Transactional
    public void deleteMember(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new RuntimeException("Member not found with id : " + id));
        memberRepository.delete(member);
    }
}