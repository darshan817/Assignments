from pydantic import BaseModel, Field
from typing import Optional

class ProductBase(BaseModel):
    name: str
    price: int

class ProductCreate(ProductBase):
    company_id: int
    category_id: Optional[int] = None  # ✅ Optional field

class ProductUpdate(BaseModel):
    name: Optional[str] = None
    price: Optional[int] = None
    company_id: Optional[int] = None
    category_id: Optional[int] = None

class ProductResponse(BaseModel):
    id: int
    name: str
    price: int
    company_id: Optional[int] = Field(alias="companyId")
    category_id: Optional[int] = Field(alias="categoryId")

    model_config = {
        "from_attributes": True,
        "populate_by_name": True,  # ✅ allows both company_id & companyId
    }
