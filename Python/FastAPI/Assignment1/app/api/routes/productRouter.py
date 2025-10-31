from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from schemas.ProductSchema import ProductCreate, ProductResponse
from api.dependencies import get_db
from models.Product import Product 
from typing import Optional
from models import Category, Company
# from logging import Logger
# logger = Logger("r")

router = APIRouter(
    prefix="/products",
    tags=["Products"]
)

@router.post("/", response_model=ProductResponse)
def create_product(product: ProductCreate, db: Session = Depends(get_db)):
    company = db.query(Company).filter(Company.id == product.company_id).first()
    if not company:
        raise HTTPException(status_code=404, detail=f"Company with id {product.company_id} not found.")

    db_product = Product(
        name=product.name,
        price=product.price,
        company_id=product.company_id,  # Make sure this is set
        category_id=product.category_id
    )

    
    db.add(db_product)
    db.commit()
    db.refresh(db_product)
    return db_product

@router.get("/getALL", response_model=list[ProductResponse])
def get_all_products(db: Session = Depends(get_db)):
    products = db.query(Product).all()
    print(f"Retrieved {len(products)} products")  # or use logging
    return products

@router.get('/getByID/{product_id}', response_model=ProductResponse)
def getByID(product_id: int, db: Session = Depends(get_db)):
    product = db.query(Product).filter(Product.id == product_id).first()
    if not product:
        raise HTTPException(
            status_code=404,
            detail=f"Product not found with id {product_id}"
        )
    return product


@router.put("/update/{product_id}", response_model=ProductResponse)
def update_product(
    product_id: int,
    product_data: ProductCreate,
    company_id: Optional[int] = None,
    category_id: Optional[int] = None,
    db: Session = Depends(get_db)
):
    # logger.info(f"Updating product with ID {product_id} - Data: {product_data}, company_id: {company_id}, category_id: {category_id}")
    
    existing_product = db.query(Product).filter(Product.id == product_id).first()
    if not existing_product:
        # logger.warning(f"Product not found with id {product_id}")
        raise HTTPException(status_code=404, detail=f"Product not found with id {product_id}")
    
    # Update basic fields
    existing_product.name = product_data.name
    existing_product.price = product_data.price

    # Update company if provided
    if company_id is not None:
        company = db.query(Company).filter(Company.id == company_id).first()
        if not company:
            # logger.warning(f"Company not found with id {company_id}")
            raise HTTPException(status_code=404, detail=f"Company not found with id {company_id}")
        existing_product.company_id = company_id

    # Update category if provided
    if category_id is not None:
        category = db.query(Category).filter(Category.id == category_id).first()
        if not category:
            # logger.warning(f"Category not found with id {category_id}")
            raise HTTPException(status_code=404, detail=f"Category not found with id {category_id}")
        existing_product.category_id = category_id

    db.commit()
    db.refresh(existing_product)

    # logger.info(f"Product updated successfully: {existing_product.id}")
    return existing_product


@router.delete("/delete/{product_id}", response_model=None)
def delete_product(product_id: int, db: Session = Depends(get_db)):
    # Find the product
    product = db.query(Product).filter(Product.id == product_id).first()
    
    if not product:
        raise HTTPException(status_code=404, detail=f"Product not found with id {product_id}")
    
    # Delete the product
    db.delete(product)
    db.commit()
    
    return {"message": f"Product with id {product_id} deleted successfully"}