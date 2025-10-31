from pydantic import BaseModel
from typing import List, Optional

from schemas.ProductSchema import ProductResponse  # import existing ProductResponse

class CategoryBase(BaseModel):
    name: str

class CategoryCreate(CategoryBase):
    product_ids: Optional[List[int]] = []  # Optional product IDs

class CategoryUpdate(BaseModel):
    name: Optional[str] = None
    product_ids: Optional[List[int]] = None

class CategoryResponse(CategoryBase):
    id: int
    name: str
    products: List[ProductResponse] = []

    model_config = {
        "from_attributes": True  # Pydantic v2 style
    }