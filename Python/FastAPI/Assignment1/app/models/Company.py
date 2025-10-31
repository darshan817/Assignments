from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import relationship

from db.database import Base

class Company(Base):

    __tablename__ = "companies"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, index=True)
    # location = Column(String)

    #Relationships
    products = relationship("Product", back_populates="company", cascade="all, delete")