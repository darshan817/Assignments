package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.DTO.CourseDTO;
import com.example.demo.DTO.CourseDetailDTO;
import com.example.demo.Model.Course;

public interface CourseRepository extends JpaRepository <Course, Long>{
    @Query("SELECT new com.example.demo.DTO.CourseDTO(c.name, c.instructor) " +
           "FROM Course c LEFT JOIN c.students s " +
           "WHERE s IS NULL")
    List<CourseDTO> findCoursesWithoutStudents();

    @Query("SELECT new com.example.demo.DTO.CourseDetailDTO(" +
           "c.id, c.name, c.instructor, COUNT(s)) " +
           "FROM Course c LEFT JOIN c.students s " +
           "GROUP BY c.id, c.name, c.instructor")
    List<CourseDetailDTO> getCourseDetailsWithStudentCount();

    @Query("SELECT new com.example.demo.DTO.CourseDetailDTO(" +
        "c.id, c.name, c.instructor, COUNT(s)) " +
        "FROM Course c LEFT JOIN c.students s " +
        "GROUP BY c.id, c.name, c.instructor " +
        "ORDER BY COUNT(s) DESC")
    List<CourseDetailDTO> findTopCourses(Pageable pageable);
}
