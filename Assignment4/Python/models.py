from pydantic import BaseModel, ValidationError, field_validator

from pydantic import BaseModel, ValidationError, field_validator

class Student(BaseModel):
    id: int
    name: str
    class_id: int
    marks: float
    gender: str
    age: int

    @field_validator("age")
    def validate_age(cls, age):
        if age > 20:
            raise ValueError("Age must be 20 or below")
        return age


class Address(BaseModel):
    id: int
    pincode: str
    city: str
    student_id: int


class Class(BaseModel):
    id: int
    name: str
