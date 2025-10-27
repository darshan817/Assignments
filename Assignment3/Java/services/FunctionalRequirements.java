package Assignment3.Java.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import Assignment3.Java.models.Address;
import Assignment3.Java.models.Student;

public class FunctionalRequirements {
    private final Service service;

    public FunctionalRequirements(Service service) {
        this.service = service;
    }

    private List<Student> applyFilters(List<Student> students, String gender, Integer age, Integer classId, String city, String pincode) {
        List<Student> filtered = new ArrayList<>(students);
        if (gender != null) {
            filtered = filtered.stream().filter(s -> gender.equals(s.getGender())).collect(Collectors.toList());
        }
        if (age != null) {
            filtered = filtered.stream().filter(s -> s.getAge() == age).collect(Collectors.toList());
        }
        if (classId != null) {
            filtered = filtered.stream().filter(s -> s.getClassId() == classId).collect(Collectors.toList());
        }
        if (city != null || pincode != null) {
            List<Integer> studentIds = service.getAddresses().stream()
                    .filter(a -> (city == null || city.equals(a.getCity())) && (pincode == null || pincode.equals(a.getPincode())))
                    .map(Address::getStudentId)
                    .collect(Collectors.toList());
            filtered = filtered.stream().filter(s -> studentIds.contains(s.getId())).collect(Collectors.toList());
        }
        return filtered;
    }

    public List<Student> topNStudents(int n, String gender, Integer classId) {
        List<Student> students = new ArrayList<>(service.getStudents());
        if (gender != null || classId != null) {
            students = applyFilters(students, gender, null, classId, null, null);
        }
        return students.stream().sorted(Comparator.comparingDouble(Student::getMarks).reversed()).limit(n).collect(Collectors.toList());
    }

    public List<Student> studentsByCity(String city) {
        List<Integer> ids = service.getAddresses().stream().filter(a -> city.equals(a.getCity())).map(Address::getStudentId).collect(Collectors.toList());
        return service.getStudents().stream().filter(s -> ids.contains(s.getId())).collect(Collectors.toList());
    }

    public List<Student> failedStudents(double passingMarks, String gender, Integer classId) {
        List<Student> students = service.getStudents().stream().filter(s -> s.getMarks() < passingMarks).collect(Collectors.toList());
        return applyFilters(students, gender, null, classId, null, null);
    }

    public List<Student> ranking(String gender, Integer classId) {
        List<Student> students = new ArrayList<>(service.getStudents());
        students = applyFilters(students, gender, null, classId, null, null);
        students.sort(Comparator.comparingDouble(Student::getMarks).reversed());
        return students;
    }
}
