from sqlalchemy import Column, String, Integer, ForeignKey
from sqlalchemy.orm import relationship

from db.database import Base

class Product(Base):
    __tablename__ = "products" 

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, index=True)
    price = Column(Integer)
    company_id = Column(Integer, ForeignKey("companies.id"))

    #relationship back to company
    company = relationship("Company", back_populates="products")

    # relation with categories
    category_id = Column(Integer, ForeignKey("categories.id"))
    category = relationship("Category", back_populates="products")
