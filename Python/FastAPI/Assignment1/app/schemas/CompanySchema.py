# --- Company Schemas ---
from pydantic import BaseModel
from typing import List, Optional

from schemas.ProductSchema import * 

class CompanyBase(BaseModel):
    name: str
    # location: str

class CompanyCreate(CompanyBase):
    product_ids: Optional[List[int]] = [] 

class CompanyResponse(CompanyBase):
    id: int
    name: str
    products: List[ProductResponse] = []
    model_config = {
        "from_attributes": True  # <-- new way in Pydantic v2
    }
