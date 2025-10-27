package com.example.demo.Controller;

import com.example.demo.DTO.CourseStudentDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.DTO.StudentWithoutCourse;
import com.example.demo.Model.Course;
import com.example.demo.Model.Student;
import com.example.demo.Service.CourseService;
import com.example.demo.Service.StudentService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @PostMapping
    public Student createStudent(@RequestBody StudentDTO req){
        Student student = new Student();
        student.setName(req.getName());
        student.setEmail(req.getEmail());
        student.setCity(req.getCity());

        Set<Course> courses = new HashSet<>();
        if (req.getCourseIds() != null) {
            for (Long courseId : req.getCourseIds()) {
                Course c = courseService.getCourse(courseId);
                courses.add(c);
            }
        }
        student.setCourses(courses);
        Student saved = studentService.saveStudent(student);
        return saved;
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
    }

    @PatchMapping("/{id}")
    // public Student updateStudent(@RequestBody Student student){
    //     return studentService.updateStudent(student);
    // }
        public Student patchStudent(Long id, StudentDTO dto) {
        Student existing = studentService.getStudent(id);
        // Update only non-null fields
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getCity() != null) existing.setCity(dto.getCity());

        // Update courses if provided
        if (dto.getCourseIds() != null) {
            Set<Course> updatedCourses = new HashSet<>();
            for (Long cid : dto.getCourseIds()) {
                Course course = courseService.getCourse(cid);
                updatedCourses.add(course);
            }
            existing.setCourses(updatedCourses);
        }

        return studentService.saveStudent(existing);
    }


    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

    @GetMapping("/by-course/{courseName}")
    public List<Student> getStudentsByCourseName(@PathVariable String courseName) {
        List<Student> students = studentService.getStudentsByCourseName(courseName);
        return students;
    }

    @GetMapping("/count-by-course")
    public List<CourseStudentDTO> getStudentCountByCourse() {
        List<CourseStudentDTO> result = studentService.getStudentCountByCourse();
        return result;
    }

    @GetMapping("/students/no-courses")
    public List<StudentWithoutCourse> getStudentsWithoutCourses() {
        return studentService.getStudentsWithoutCourses();
    }

    @GetMapping("/search")
    public List<Student> searchStudents(@RequestParam String city, @RequestParam String instructorName) {
        return studentService.getStudentsByCityAndInstructor(city, instructorName);
    }

}   