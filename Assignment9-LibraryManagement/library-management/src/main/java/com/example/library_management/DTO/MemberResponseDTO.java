package com.example.library_management.DTO;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
    
    private Long id;
    private String name;
    private Set<String> borrowedBookTitles;

}
