class DeleteOperation:
    def __init__(self, service):
        self.service = service

    def delete_student(self, student_id: int):
        student = next((s for s in self.service.students if s.id == student_id), None)
        if not student:
            print(f"No student found with ID {student_id}")
            return

        # Remove student
        self.service.students = [s for s in self.service.students if s.id != student_id]
        print(f"Student with ID {student_id} deleted successfully.")

        # Remove corresponding addresses
        self.service.addresses = [a for a in self.service.addresses if a.student_id != student_id]

        # Check if class still has students
        class_id = student.class_id
        if all(s.class_id != class_id for s in self.service.students):
            self.service.classes = [c for c in self.service.classes if c.id != class_id]
            print(f"Class with ID {class_id} deleted because it has no students left.")
