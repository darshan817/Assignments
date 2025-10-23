package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.CourseStudentDTO;
import com.example.demo.DTO.StudentWithoutCourse;
import com.example.demo.Model.Student;
import com.example.demo.Repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudent(Long id){
        return studentRepository.findById(id).orElseThrow(()->new RuntimeException("Student not found with id "+id));
    }

    public void deleteStudent(Long id){
         studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByCourseName(String courseName) {
        return studentRepository.findStudentsByCourseName(courseName);
    }

    public List<CourseStudentDTO> getStudentCountByCourse() {
        return studentRepository.getStudentCountByCourse();
    }

    public List<StudentWithoutCourse> getStudentsWithoutCourses() {
        return studentRepository.findStudentsWithoutCourses();
    }

    public List<Student> getStudentsByCityAndInstructor(String city, String instructorName) {
        return studentRepository.findStudentsByCityAndInstructor(city, instructorName);
    }
}