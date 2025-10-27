package Assignment4.Java;
import java.util.*; import Assignment4.Java.models.*; import Assignment4.Java.services.*;
public class Main {
    public static void main(String[] args){
        Service service=new Service();
        service.addStudent(new Student(1,"Alice",101,85.5,"Female",19));
        service.addStudent(new Student(2,"Bob",102,45.0,"Male",18));
        service.addStudent(new Student(3,"Clara",101,92.0,"Female",20));
        service.addAddress(new Address(1,"123456","New York",1));
        service.addAddress(new Address(2,"123455","Boston",2));
        service.addAddress(new Address(3,"123456","New York",3));
        service.addClass(new SchoolClass(101,"Physics"));
        service.addClass(new SchoolClass(102,"Chemistry"));
        FunctionalRequirements fr=new FunctionalRequirements(service);
        PaginationService p=new PaginationService(service);
        DeleteOperation d=new DeleteOperation(service);
        System.out.println("\nAll Students:");service.getStudents().forEach(System.out::println);
        System.out.println("\nStudents in New York:");fr.studentsByCity("New York").forEach(System.out::println);
        System.out.println("\nRanking:");fr.ranking(null,null).forEach(System.out::println);
        System.out.println("\nPaginated (Female students, first 2):");Map<String,Object> filters=new HashMap<>();filters.put("gender","Female");p.getStudents(filters,null,true,0,2).forEach(System.out::println);
        System.out.println("\n");d.deleteStudent(2);
        System.out.println("\nAfter deletion, all students:");service.getStudents().forEach(System.out::println);
    }
}
