package Assignment6;
import java.util.ArrayList;
import java.util.stream.Stream;


public class Task1 {

    
    public static class Employee {
        private int id;
        private String name;
        private double salary;
        private String department;

        public Employee(String name, int id, double salary, String department) {
            this.id = id;
            this.name = name;
            this.salary = salary;
            this.department = department;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        public String getDepartment() {
            return department;
        }

        // Override toString() for readable output
        @Override
        public String toString() {
            return "Employee [ID=" + id + ", Name=" + name + ", Salary=" + salary + ", Department=" + department + "]";
        }
    }

    public static void main(String[] args) {

        
        Employee emp1 = new Employee("Alice", 1, 50000, "HR");
        Employee emp2 = new Employee("Bob", 2, 60000, "IT");
        Employee emp3 = new Employee("Charlie", 3, 70000, "Finance");
        Employee emp4 = new Employee("Diana", 4, 80000, "Marketing");
        Employee emp5 = new Employee("Eve", 5, 90000, "Sales");

       
        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(emp1);
        employeeList.add(emp2);
        employeeList.add(emp3);
        employeeList.add(emp4);
        employeeList.add(emp5);

        // System.out.println("All Employees:");
        // for (Employee emp : employeeList) {
        //     System.out.println(emp);
        // }

        // Filter and print employees in IT Department
        System.out.println("\nEmployees in IT Department:");
        employeeList.stream()
                .filter(x -> x.department.equals("IT"))
                .forEach(System.out::println);
        
        double x = employeeList.stream().map(Employee :: getSalary).reduce((a,b) -> a+b).get();
        System.out.println("\nCompare salary using reduce : " + x);

        System.out.println("\nConverting employee names to upper case :- ");
        employeeList.stream().map(e->e.getName().toUpperCase()).forEach(System.out::println);
       
    }   
}