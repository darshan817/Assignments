from service import Service
from functional_requirements import FunctionalRequirements
from delete_operation import DeleteOperation
from pagination_service import PaginationService

# Initialize service
service = Service()

# Add students
service.add_student(id=1, name="Alice", class_id=101, marks=85.5, gender="Female", age=19)
service.add_student(id=2, name="Bob", class_id=102, marks=45.0, gender="Male", age=18)
service.add_student(id=3, name="Clara", class_id=101, marks=92.0, gender="Female", age=20)

# Add addresses
service.add_address(id=1, pincode="123456", city="New York", student_id=1)
service.add_address(id=2, pincode="123455", city="Boston", student_id=2)
service.add_address(id=3, pincode="123454", city="Chicago", student_id=3)

# Add classes
service.add_class(id=101, name="Mathematics")
service.add_class(id=102, name="Science")

# Instantiate helpers
getter = FunctionalRequirements(service)
deleter = DeleteOperation(service)
pagination = PaginationService(service)

# Examples
print("\nAll Students:")
service.get_students()

print("\nStudents in New York:")
print(getter.students_by_city("New York"))

print("\nRanking:")
print(getter.ranking())

print("\nPaginated (Female students, first 2):")
print(pagination.get_students(filters={"gender": "Female"}, start=0, end=2))

# Delete student
deleter.delete_student(2)
