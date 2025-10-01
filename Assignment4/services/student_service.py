from pydantic import ValidationError
from models import Student
import pandas as pd
from . import FileHandler

class StudentService:
    def __init__(self, file_handler: FileHandler):
        self.file_handler = file_handler
        self.filename = 'student_data.json'
        self.students = [Student(**s) for s in self.file_handler.load(self.filename)]

    def add_student(self, **student_data):
        try:
            student = Student(**student_data)
            self.students.append(student)
            self.file_handler.save(self.filename, self.students)
        except ValidationError as e:
            print("Validation Error while adding student:", e.errors())
        except Exception as e:
            print("Unexpected Error while adding student:", e)

    def get_students(self):
        if not self.students:
            print("No students found.")
            return
        df = pd.DataFrame([s.dict() for s in self.students])
        print("\nStudents Table:\n", df)

    def rank_students(self, top_n=5):
        """Rank students by marks and save top N students."""
        if not self.students:
            print("No students available to rank.")
            return

        # Sort by marks descending
        sorted_students = sorted(self.students, key=lambda s: s.marks, reverse=True)
        top_students = sorted_students[:top_n]

        # Save top students to JSON
        self.file_handler.save(self.ranking_filename, top_students)
        print(f"\nTop {top_n} Students saved to '{self.ranking_filename}'")

    def get_top_students(self):
        """Load and display top students."""
        top_students_data = self.file_handler.load(self.ranking_filename)
        if not top_students_data:
            print("No ranked students found. Please run rank_students() first.")
            return
        df = pd.DataFrame(top_students_data)
        print("\nTop Students Table:\n", df)
