from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from models.Company import Company
from schemas.CompanySchema import CompanyCreate, CompanyResponse
from api.dependencies import get_db
from models.Product import Product

router = APIRouter(
    prefix="/companies",
    tags=["Companies"]
)

@router.post("/create", response_model=CompanyResponse)
def create_company(company: CompanyCreate, db: Session = Depends(get_db)):
    db_company = Company(name=company.name)
    db.add(db_company)
    db.commit()
    db.refresh(db_company)
    return db_company

@router.get("/getAll", response_model=list[CompanyResponse])
def get_companies(db: Session = Depends(get_db)):
    return db.query(Company).all()

@router.get('/getByID/{company_id}', response_model=CompanyResponse)
def getByID(company_id:int, db: Session = Depends(get_db)):
    company = db.query(Company).filter(Company.id == company_id).first()
    if not company:
        raise HTTPException(
            status_code=404,
            detail=f"Company not found with id {company_id}"
        )
    return company

@router.put('/update/{company_id}', response_model=CompanyResponse)
def update(company_id: int, company_data: CompanyCreate, db: Session = Depends(get_db)):
    existing_company = db.query(Company).filter(Company.id == company_id).first()

    if not existing_company:
        raise HTTPException(
            status_code=404,
            detail=f"Company not found with id {company_id}"
        )
    
    # Update company name
    existing_company.name = company_data.name

    # If product IDs provided, validate and update relationships
    if company_data.product_ids:
        existing_products = db.query(Product.id).filter(Product.id.in_(company_data.product_ids)).all()
        existing_ids = [p.id for p in existing_products]
        missing_ids = set(company_data.product_ids) - set(existing_ids)
        if missing_ids:
            raise HTTPException(
                status_code=404,
                detail=f"Products with IDs {list(missing_ids)} do not exist."
            )

        # Unlink all current products first
        db.query(Product).filter(Product.company_id == existing_company.id).update(
            {Product.company_id: None}, synchronize_session=False
        )

        # Link the new products to this company
        db.query(Product).filter(Product.id.in_(company_data.product_ids)).update(
            {Product.company_id: existing_company.id}, synchronize_session=False
        )

    db.commit()
    db.refresh(existing_company)
    return existing_company

@router.delete('/delete/{company_id}', response_model=None)
def delete_company(company_id: int, db: Session = Depends(get_db)):
    company = db.query(Company).filter(Company.id == company_id).first()

    if not company:
        raise HTTPException(
            status_code=404,
            detail=f"Company with id {company_id} not found"
        )

    db.delete(company)
    db.commit()
    return {"message": f"Company with id {company_id} and its related products were deleted successfully."}