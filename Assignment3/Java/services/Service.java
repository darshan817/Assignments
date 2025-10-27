package Assignment3.Java.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Assignment3.Java.models.Address;
import Assignment3.Java.models.SchoolClass;
import Assignment3.Java.models.Student;

public class Service {
    private List<Student> students = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
    private List<SchoolClass> classes = new ArrayList<>();

    public void addStudent(Student s) {
        Optional<Student> existing = students.stream().filter(st -> st.getId() == s.getId()).findFirst();
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Student with id " + s.getId() + " already exists.");
        }
        students.add(s);
    }

    public void addAddress(Address a) {
        addresses.add(a);
    }

    public void addClass(SchoolClass c) {
        classes.add(c);
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    public List<SchoolClass> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public Optional<Student> findStudentById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }

    public void deleteStudent(int id) {
        students = students.stream().filter(s -> s.getId() != id).collect(Collectors.toList());
        // also remove addresses linked to student
        addresses = addresses.stream().filter(a -> a.getStudentId() != id).collect(Collectors.toList());
    }
}

