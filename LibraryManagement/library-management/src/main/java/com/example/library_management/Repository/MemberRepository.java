package com.example.library_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.example.library_management.Entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
}
