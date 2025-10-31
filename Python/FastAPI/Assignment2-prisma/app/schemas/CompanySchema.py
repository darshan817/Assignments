# app/schemas/CompanySchema.py
from pydantic import BaseModel
from typing import List, Optional
from app.schemas.ProductSchema import ProductResponse

class CompanyBase(BaseModel):
    name: str
    location: str

class CompanyCreate(CompanyBase):
    product_ids: Optional[List[int]] = None

class CompanyResponse(CompanyBase):
    id: int
    products: List[ProductResponse] = []  # ✅ full product objects, not int IDs

    model_config = {
        "from_attributes": True
    }
