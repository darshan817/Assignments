from pydantic import ValidationError
from models import Class
import pandas as pd
from . import FileHandler

class ClassService:
    def __init__(self, file_handler: FileHandler):
        self.file_handler = file_handler
        self.filename = 'class_data.json'
        self.classes = [Class(**c) for c in self.file_handler.load(self.filename)]

    def add_class(self, **class_data):
        try:
            class_ = Class(**class_data)
            self.classes.append(class_)
            self.file_handler.save(self.filename, self.classes)
        except ValidationError as e:
            print("Validation Error while adding class:", e.errors())
        except Exception as e:
            print("Unexpected Error while adding class:", e)

    def get_classes(self):
        if not self.classes:
            print("No classes found.")
            return
        df = pd.DataFrame([c.dict() for c in self.classes])
        print("\nClasses Table:\n", df)
