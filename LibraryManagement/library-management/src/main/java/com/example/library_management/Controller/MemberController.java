package com.example.library_management.Controller;

import com.example.library_management.DTO.MemberRequestDTO;
import com.example.library_management.DTO.MemberResponseDTO;
import com.example.library_management.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping
    public MemberResponseDTO createMember(@RequestBody MemberRequestDTO member){
        return memberService.createMember(member);
    }
 
    @GetMapping
    public List<MemberResponseDTO> getAllMembers(){
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public MemberResponseDTO getMemberById(@PathVariable Long id){
        return memberService.getMemberById(id);
    }

    @PatchMapping("/{id}")
    public MemberResponseDTO updateMember(@PathVariable Long id, @RequestBody MemberRequestDTO member){
        return memberService.updateMember(id, member);
    }

    @DeleteMapping("/{id}")
    public void deleteMemberById(@PathVariable Long id){
        memberService.deleteMember(id);
    }

    @GetMapping("/{id}/borrowed-books")
    public Set<String> getBorrowedBooks(@PathVariable Long id) {
        MemberResponseDTO memberDTO = memberService.getMemberById(id);
        return memberDTO.getBorrowedBookTitles();
    }
    

}
