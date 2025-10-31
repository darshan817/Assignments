from pydantic import BaseModel
from typing import List, Optional

class ProductBase(BaseModel):
    name: str
    price: int

class ProductCreate(ProductBase):
    company_id: int  # Now client must provide company ID
    category_id: Optional[int] = None  # Optional, if you have categories


class ProductResponse(ProductBase):
    id: int
    company_id: int
    category_id: Optional[int]

    model_config = {
        "from_attributes": True  # <-- new way in Pydantic v2
    }