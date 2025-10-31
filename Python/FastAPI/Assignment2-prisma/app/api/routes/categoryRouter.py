from fastapi import APIRouter, Depends, HTTPException
from typing import List
# from prisma.models import Category, Product
from app.schemas.CategorySchema import CategoryCreate, CategoryResponse, CategoryUpdate
from app.api.dependencies import get_db

router = APIRouter(
    prefix="/categories",
    tags=["Categories"]
)

# --------------------
# Get all categories
# --------------------
@router.get("/getAll", response_model=List[CategoryResponse])
async def get_all_categories(db=Depends(get_db)):
    categories = await db.category.find_many(
        include={"products": True}  # ✅ Matches your schema relation
    )
    print(f"Retrieved {len(categories)} categories")
    return categories


# --------------------
# Get category by ID
# --------------------
@router.get("/getbyID/{category_id}", response_model=CategoryResponse)
async def get_category_by_id(category_id: int, db=Depends(get_db)):
    category = await db.category.find_unique(
        where={"id": category_id},
        include={"products": True}
    )
    if not category:
        raise HTTPException(status_code=404, detail=f"Category not found with id {category_id}")
    return category


# --------------------
# Add new category
# --------------------
@router.post("/add", response_model=CategoryResponse)
async def add_category(category_data: CategoryCreate, db=Depends(get_db)):
    # ✅ Validate product IDs (if provided)
    if category_data.product_ids:
        products = await db.product.find_many(
            where={"id": {"in": category_data.product_ids}}
        )
        if len(products) != len(category_data.product_ids):
            existing_ids = [p.id for p in products]
            missing_ids = set(category_data.product_ids) - set(existing_ids)
            raise HTTPException(status_code=404, detail=f"Products not found: {missing_ids}")
    else:
        products = []

    # ✅ Create category and connect existing products
    new_category = await db.category.create(
        data={
            "name": category_data.name,
            "products": {
                "connect": [{"id": p.id} for p in products]
            } if products else {}
        },
        include={"products": True}
    )

    print(f"Added new category with id {new_category.id}")
    return new_category


# --------------------
# Update category
# --------------------
@router.put("/update/{category_id}", response_model=CategoryResponse)
async def update_category(category_id: int, category_data: CategoryUpdate, db=Depends(get_db)):
    category = await db.category.find_unique(where={"id": category_id})
    if not category:
        raise HTTPException(status_code=404, detail=f"Category not found with id {category_id}")

    update_data = {}

    # ✅ Update category name
    if category_data.name:
        update_data["name"] = category_data.name

    # ✅ Update associated products
    if category_data.product_ids is not None:
        products = await db.product.find_many(
            where={"id": {"in": category_data.product_ids}}
        )
        if len(products) != len(category_data.product_ids):
            existing_ids = [p.id for p in products]
            missing_ids = set(category_data.product_ids) - set(existing_ids)
            raise HTTPException(status_code=404, detail=f"Products not found: {missing_ids}")
        update_data["products"] = {
            "set": [{"id": p.id} for p in products]  # ✅ resets relations
        }

    updated_category = await db.category.update(
        where={"id": category_id},
        data=update_data,
        include={"products": True}
    )

    print(f"Updated category with id {updated_category.id}")
    return updated_category


# --------------------
# Delete category
# --------------------
@router.delete("/delete/{category_id}", response_model=dict)
async def delete_category(category_id: int, db=Depends(get_db)):
    category = await db.category.find_unique(where={"id": category_id})
    if not category:
        raise HTTPException(status_code=404, detail=f"Category not found with id {category_id}")

    await db.category.delete(where={"id": category_id})
    print(f"Deleted category with id {category_id}")
    return {"message": f"Category with id {category_id} deleted successfully"}
