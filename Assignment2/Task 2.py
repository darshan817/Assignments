class Employee:
    def __init__(self, emp_id, name, department, salary):
        self.emp_id = emp_id
        self.name = name
        self.department = department
        self.salary = salary

    def __repr__(self):
        return f"{self.emp_id} - {self.name} - {self.department} - {self.salary}"

# Sample data
employees = [
    Employee(101, "Alice", "IT", 70000),
    Employee(102, "Bob", "HR", 50000),
    Employee(103, "Charlie", "IT", 60000),
    Employee(104, "David", "HR", 55000),
    Employee(105, "Eve", "IT", 70000),
    Employee(106, "Alice", "IT", 75000),  # Same name & department as 101
]

# Sort by Department (asc), Name (asc), Salary (desc)
sorted_employees = sorted(
    employees, 
    key=lambda e: (e.department, e.name, -e.salary)  # negative salary for descending
)

# Traverse using iterator
print("Sorted by Department -> Name -> Salary (descending if tie):")
it = iter(sorted_employees)
while True:
    try:
        print(next(it))
    except StopIteration:
        break
