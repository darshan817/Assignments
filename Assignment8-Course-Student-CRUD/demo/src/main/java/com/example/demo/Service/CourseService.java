package com.example.demo.Service;

import java.util.List;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.CourseDTO;
import com.example.demo.DTO.CourseDetailDTO;
import com.example.demo.Model.Course;
import com.example.demo.Repository.CourseRepository;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(Course course){
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourseData) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));

        if (updatedCourseData.getName() != null) {
            existingCourse.setName(updatedCourseData.getName());
        }
        if (updatedCourseData.getInstructor() != null) {
            existingCourse.setInstructor(updatedCourseData.getInstructor());
        }
        return courseRepository.save(existingCourse);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourse(Long id){
        return courseRepository.findById(id).orElseThrow(()->new RuntimeException("Student not found with id "+id));
    }

    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }

    public List<CourseDTO> getCoursesWithoutStudents() {
        return courseRepository.findCoursesWithoutStudents();
    }

    public Course updateInstructorById(Long id, String newInstructor) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));

        course.setInstructor(newInstructor);
        return courseRepository.save(course);
    }

    public List<CourseDetailDTO> getCourseDetailsWithStudentCount() {
        return courseRepository.getCourseDetailsWithStudentCount();
    }

    public List<CourseDetailDTO> getTopNCourses(int n) {
        Pageable limit = PageRequest.of(0, n);
        return courseRepository.findTopCourses(limit);
    }

}
