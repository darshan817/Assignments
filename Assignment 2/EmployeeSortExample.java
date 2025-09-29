import java.util.*;

// Employee class implementing Comparable
class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    // Comparable: default sorting by Department → Name → Salary(desc)
    @Override
    public int compareTo(Employee other) {
        int deptCompare = this.department.compareTo(other.department);
        if (deptCompare != 0) return deptCompare;

        int nameCompare = this.name.compareTo(other.name);
        if (nameCompare != 0) return nameCompare;

        // Salary descending
        return Double.compare(other.salary, this.salary);
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + department + " | " + salary;
    }
}

// Comparator example (same logic as Comparable, just for demonstration)
class EmployeeComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        int deptCompare = e1.getDepartment().compareTo(e2.getDepartment());
        if (deptCompare != 0) return deptCompare;

        int nameCompare = e1.getName().compareTo(e2.getName());
        if (nameCompare != 0) return nameCompare;

        return Double.compare(e2.getSalary(), e1.getSalary());
    }
}

public class EmployeeSortExample {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        // Sample Employees
        employees.add(new Employee(101, "Alice", "HR", 50000));
        employees.add(new Employee(102, "Bob", "IT", 60000));
        employees.add(new Employee(103, "Charlie", "HR", 55000));
        employees.add(new Employee(104, "David", "IT", 58000));
        employees.add(new Employee(105, "Eve", "Finance", 62000));
        employees.add(new Employee(106, "Bob", "IT", 65000));

        System.out.println("Before Sorting:");
        Iterator<Employee> it = employees.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // Sorting using Comparable
        Collections.sort(employees);

        System.out.println("\nAfter Sorting using Comparable:");
        it = employees.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // Sorting using Comparator
        Collections.sort(employees, new EmployeeComparator());

        System.out.println("\nAfter Sorting using Comparator:");
        it = employees.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
