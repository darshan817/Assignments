from fastapi import APIRouter, Depends, HTTPException
from typing import List
from app.schemas.CompanySchema import CompanyCreate, CompanyResponse
from app.api.dependencies import get_db

router = APIRouter(
    prefix="/companies",
    tags=["Companies"]
)

# --------------------
# Create new company
# --------------------
@router.post("/create", response_model=CompanyResponse)
async def create_company(company: CompanyCreate, db=Depends(get_db)):
    # ✅ Check if company with the same name already exists
    existing = await db.company.find_first(where={"name": company.name})
    if existing:
        raise HTTPException(
            status_code=400,
            detail=f"Company with name '{company.name}' already exists"
        )

    # ✅ Validate and connect products if provided
    products = []
    if company.product_ids:
        products = await db.product.find_many(where={"id": {"in": company.product_ids}})
        if len(products) != len(company.product_ids):
            existing_ids = [p.id for p in products]
            missing_ids = set(company.product_ids) - set(existing_ids)
            raise HTTPException(
                status_code=404,
                detail=f"Products not found: {missing_ids}"
            )

    # ✅ Create company
    new_company = await db.company.create(
        data={
            "name": company.name,
            "location": company.location,
            "products": {
                "connect": [{"id": p.id} for p in products]
            } if products else {}
        },
        include={"products": True}
    )

    print(f"Created company with id {new_company.id}")
    return new_company


# --------------------
# Get all companies
# --------------------
@router.get("/getAll", response_model=List[CompanyResponse])
async def get_companies(db=Depends(get_db)):
    companies = await db.company.find_many(include={"products": True})
    print(f"Retrieved {len(companies)} companies")
    return companies


# --------------------
# Get company by ID
# --------------------
@router.get("/getByID/{company_id}", response_model=CompanyResponse)
async def get_by_id(company_id: int, db=Depends(get_db)):
    company = await db.company.find_unique(
        where={"id": company_id},
        include={"products": True}
    )
    if not company:
        raise HTTPException(
            status_code=404,
            detail=f"Company not found with id {company_id}"
        )
    return company


# --------------------
# Update company
# --------------------
@router.put("/update/{company_id}", response_model=CompanyResponse)
async def update_company(company_id: int, company_data: CompanyCreate, db=Depends(get_db)):
    existing_company = await db.company.find_unique(where={"id": company_id})
    if not existing_company:
        raise HTTPException(
            status_code=404,
            detail=f"Company not found with id {company_id}"
        )

    # ✅ Prevent duplicate names during update
    if company_data.name:
        duplicate = await db.company.find_first(where={"name": company_data.name})
        if duplicate and duplicate.id != company_id:
            raise HTTPException(
                status_code=400,
                detail=f"Another company with name '{company_data.name}' already exists"
            )

    update_data = {
        "name": company_data.name or existing_company.name,
        "location": company_data.location or existing_company.location
    }

    # ✅ Handle product association updates
    if company_data.product_ids:
        products = await db.product.find_many(where={"id": {"in": company_data.product_ids}})
        if len(products) != len(company_data.product_ids):
            existing_ids = [p.id for p in products]
            missing_ids = set(company_data.product_ids) - set(existing_ids)
            raise HTTPException(
                status_code=404,
                detail=f"Products not found: {missing_ids}"
            )
        update_data["products"] = {"set": [{"id": p.id} for p in products]}

    updated_company = await db.company.update(
        where={"id": company_id},
        data=update_data,
        include={"products": True}
    )

    print(f"Updated company with id {updated_company.id}")
    return updated_company


# --------------------
# Delete company
# --------------------
@router.delete("/delete/{company_id}", response_model=dict)
async def delete_company(company_id: int, db=Depends(get_db)):
    company = await db.company.find_unique(where={"id": company_id})
    if not company:
        raise HTTPException(
            status_code=404,
            detail=f"Company not found with id {company_id}"
        )

    await db.company.delete(where={"id": company_id})
    print(f"Deleted company with id {company_id}")
    return {"message": f"Company with id {company_id} deleted successfully"}
