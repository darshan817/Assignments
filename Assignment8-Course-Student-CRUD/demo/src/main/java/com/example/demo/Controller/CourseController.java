package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.CourseDTO;
import com.example.demo.DTO.CourseDetailDTO;
import com.example.demo.Model.Course;
import com.example.demo.Service.CourseService;
import com.example.demo.Service.StudentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired StudentService studentRepository;

    @PostMapping
    public Course saveCourse(@RequestBody Course course){
        return courseService.saveCourse(course);
    }

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id){
        return courseService.getCourse(id);
    }

    // @PatchMapping("/{id}")
    // public Course updateCourse(@RequestBody Course course){
    //     return courseService.save(course) ;
    // }
    @PatchMapping("/{id}")
    public Course updateCoursePartially(@PathVariable Long id, @RequestBody Course updates) {
    Course updatedCourse = courseService.updateCourse(id, updates);
    return updatedCourse;
}

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }

    @GetMapping("/without-students")
    public List<CourseDTO> getCoursesWithoutStudents() {
        List<CourseDTO> courses = courseService.getCoursesWithoutStudents();
        return courses;
    }

    @PatchMapping("/{id}/instructor")
    public Course updateInstructor(
            @PathVariable Long id,
            @RequestParam String instructor) {
        return courseService.updateInstructorById(id, instructor);
    }

    @GetMapping("/details-with-student-count")
    public List<CourseDetailDTO> getCourseDetailsWithStudentCount() {
        return courseService.getCourseDetailsWithStudentCount();
    }

    @GetMapping("/top/{n}")
    public List<CourseDetailDTO> getTopNCourses(@PathVariable int n) {
        return courseService.getTopNCourses(n);
    }

}
