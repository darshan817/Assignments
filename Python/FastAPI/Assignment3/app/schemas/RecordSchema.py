from pydantic import BaseModel
from typing import Optional

class RecordCreate(BaseModel):
    name: str
    email: str
    age: int
    country: str

class RecordResponse(RecordCreate):
    id: int
