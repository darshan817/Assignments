import pandas as pd
from pydantic import ValidationError
from models import Student, Address, Class

class Service:
    def __init__(self):
        self.students = []
        self.addresses = []
        self.classes = []

    def add_student(self, **student_data):
        try:
            student = Student(**student_data)
            self.students.append(student)
        except ValidationError as e:
            print("Validation Error while adding student:", e.errors())
        except Exception as e:
            print("Unexpected Error while adding student:", e)

    def add_address(self, **address_data):
        try:
            address = Address(**address_data)
            self.addresses.append(address)
        except ValidationError as e:
            print("Validation Error while adding address:", e.errors())

    def add_class(self, **class_data):
        try:
            class_ = Class(**class_data)
            self.classes.append(class_)
        except ValidationError as e:
            print("Validation Error while adding class:", e.errors())

    def get_students(self):
        if not self.students:
            print("No students found.")
            return
        df = pd.DataFrame([s.model_dump() for s in self.students])
        print("\nStudents Table:\n", df)

    def get_addresses(self):
        if not self.addresses:
            print("No addresses found.")
            return
        df = pd.DataFrame([a.model_dump() for a in self.addresses])
        print("\nAddresses Table:\n", df)

    def get_classes(self):
        if not self.classes:
            print("No classes found.")
            return
        df = pd.DataFrame([c.model_dump() for c in self.classes])
        print("\nClasses Table:\n", df)
