package Assignment3.Java;

import java.util.HashMap;
import java.util.Map;

import Assignment3.Java.models.Address;
import Assignment3.Java.models.SchoolClass;
import Assignment3.Java.models.Student;
import Assignment3.Java.services.DeleteOperation;
import Assignment3.Java.services.FunctionalRequirements;
import Assignment3.Java.services.PaginationService;
import Assignment3.Java.services.Service;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();

        // Add students
        service.addStudent(new Student(1, "Alice", 101, 85.5, "Female", 19));
        service.addStudent(new Student(2, "Bob", 102, 45.0, "Male", 18));
        service.addStudent(new Student(3, "Clara", 101, 92.0, "Female", 20));

        // Add addresses
        service.addAddress(new Address(1, "123456", "New York", 1));
        service.addAddress(new Address(2, "123455", "Boston", 2));
        service.addAddress(new Address(3, "123456", "New York", 3));

        // Add classes
        service.addClass(new SchoolClass(101, "Physics"));
        service.addClass(new SchoolClass(102, "Chemistry"));

        FunctionalRequirements fr = new FunctionalRequirements(service);
        PaginationService pagination = new PaginationService(service);
        DeleteOperation deleter = new DeleteOperation(service);

        System.out.println("\nAll Students:");
        service.getStudents().forEach(System.out::println);

        System.out.println("\nStudents in New York:");
        fr.studentsByCity("New York").forEach(System.out::println);

        System.out.println("\nRanking:");
        fr.ranking(null, null).forEach(System.out::println);

        System.out.println("\nPaginated (Female students, first 2):");
        Map<String,Object> filters = new HashMap<>();
        filters.put("gender", "Female");
        pagination.getStudents(filters, null, true, 0, 2).forEach(System.out::println);

        // Delete student
        System.out.println("\n");
        deleter.deleteStudent(2);

        System.out.println("\nAfter deletion, all students:");
        service.getStudents().forEach(System.out::println);
    }
}