package com.example.demo.Repository;

import com.example.demo.DTO.CourseStudentDTO;
import com.example.demo.DTO.StudentWithoutCourse;
import com.example.demo.Model.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s JOIN FETCH s.courses")
    List<Student> findAllWithCourses();

    
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.name = :courseName")
    List<Student> findStudentsByCourseName(@Param("courseName") String courseName);

    @Query("SELECT new com.example.demo.DTO.CourseStudentDTO(c.name, COUNT(s)) " +
           "FROM Student s JOIN s.courses c " +
           "GROUP BY c.name")
    List<CourseStudentDTO> getStudentCountByCourse();

    @Query("SELECT new com.example.demo.DTO.StudentWithoutCourse(s.id, s.name, s.email) " +
           "FROM Student s LEFT JOIN s.courses c " +
           "WHERE c IS NULL")
    List<StudentWithoutCourse> findStudentsWithoutCourses();


    @Query("SELECT DISTINCT s FROM Student s " +
       "JOIN s.courses c " +
       "WHERE s.city = :city AND c.instructor = :instructorName")
    List<Student> findStudentsByCityAndInstructor(
        @Param("city") String city,
        @Param("instructorName") String instructorName);
}