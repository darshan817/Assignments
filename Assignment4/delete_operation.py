from services.student_service import StudentService
from services.address_service import AddressService
from services.class_service import ClassService

class DeleteOperation:
    def __init__(self, student_service: StudentService, address_service: AddressService, class_service: ClassService):
        self.student_service = student_service
        self.address_service = address_service
        self.class_service = class_service

    def delete_student(self, student_id: int):
        # Find student
        student = next((s for s in self.student_service.students if s.id == student_id), None)
        if not student:
            print(f"No student found with ID {student_id}")
            return

        # Remove student
        self.student_service.students = [s for s in self.student_service.students if s.id != student_id]
        self.student_service.file_handler.save(self.student_service.filename, self.student_service.students)
        print(f"Student with ID {student_id} deleted successfully.")

        # Remove corresponding addresses
        self.address_service.addresses = [a for a in self.address_service.addresses if a.student_id != student_id]
        self.address_service.file_handler.save(self.address_service.filename, self.address_service.addresses)

        # Check if class still has students
        class_id = student.class_id
        if all(s.class_id != class_id for s in self.student_service.students):
            self.class_service.classes = [c for c in self.class_service.classes if c.id != class_id]
            self.class_service.file_handler.save(self.class_service.filename, self.class_service.classes)
            print(f"Class with ID {class_id} deleted because it has no students left.")

    def delete_address(self, address_id: int):
        address = next((a for a in self.address_service.addresses if a.id == address_id), None)
        if not address:
            print(f"No address found with ID {address_id}")
            return
        self.address_service.addresses = [a for a in self.address_service.addresses if a.id != address_id]
        self.address_service.file_handler.save(self.address_service.filename, self.address_service.addresses)
        print(f"Address with ID {address_id} deleted successfully.")

    def delete_class(self, class_id: int):
        cls = next((c for c in self.class_service.classes if c.id == class_id), None)
        if not cls:
            print(f"No class found with ID {class_id}")
            return

        # Check if any students belong to this class
        if any(s.class_id == class_id for s in self.student_service.students):
            print(f"Cannot delete class {class_id} because it still has students.")
            return

        self.class_service.classes = [c for c in self.class_service.classes if c.id != class_id]
        self.class_service.file_handler.save(self.class_service.filename, self.class_service.classes)
        print(f"Class with ID {class_id} deleted successfully.")
