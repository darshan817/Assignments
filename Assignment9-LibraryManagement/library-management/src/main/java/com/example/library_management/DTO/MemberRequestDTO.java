package com.example.library_management.DTO;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {
    private String name;
    private Set<Long> borrowedBookIds;
}
