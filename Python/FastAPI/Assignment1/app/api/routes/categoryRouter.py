from fastapi import APIRouter, HTTPException
from typing import List
from prisma import Prisma
from schemas.CategorySchema import CategoryCreate, CategoryResponse, CategoryUpdate

router = APIRouter(
    prefix="/categories",
    tags=["Categories"]
)

# Create a single Prisma client instance
db = Prisma()

# --------------------
# Get all categories
# --------------------
@router.get("/getAll", response_model=List[CategoryResponse])
async def get_all_categories():
    await db.connect()
    categories = await db.category.find_many(
        include={"products": True}
    )
    await db.disconnect()

    if not categories:
        return []
    return categories


# --------------------
# Get category by ID
# --------------------
@router.get("/getbyID/{category_id}", response_model=CategoryResponse)
async def get_category_by_id(category_id: int):
    await db.connect()
    category = await db.category.find_unique(
        where={"id": category_id},
        include={"products": True}
    )
    await db.disconnect()

    if not category:
        raise HTTPException(status_code=404, detail=f"Category not found with id {category_id}")
    return category


# --------------------
# Add new category (unique name enforced)
# --------------------
@router.post("/add", response_model=CategoryResponse)
async def add_category(category_data: CategoryCreate):
    await db.connect()

    # Check if category with same name already exists
    existing = await db.category.find_first(where={"name": category_data.name})
    if existing:
        await db.disconnect()
        raise HTTPException(status_code=400, detail=f"Category '{category_data.name}' already exists")

    # Create category
    new_category = await db.category.create(
        data={"name": category_data.name}
    )

    # Optionally associate products (if product_ids provided)
    if category_data.product_ids:
        for pid in category_data.product_ids:
            product = await db.product.find_unique(where={"id": pid})
            if not product:
                await db.disconnect()
                raise HTTPException(status_code=404, detail=f"Product with id {pid} not found")
            await db.product.update(
                where={"id": pid},
                data={"categoryId": new_category.id}
            )

    await db.disconnect()
    return new_category


# --------------------
# Update category
# --------------------
@router.put("/update/{category_id}", response_model=CategoryResponse)
async def update_category(category_id: int, category_data: CategoryUpdate):
    await db.connect()
    category = await db.category.find_unique(where={"id": category_id})

    if not category:
        await db.disconnect()
        raise HTTPException(status_code=404, detail=f"Category not found with id {category_id}")

    # Update name (ensure uniqueness)
    if category_data.name:
        existing = await db.category.find_first(where={"name": category_data.name})
        if existing and existing.id != category_id:
            await db.disconnect()
            raise HTTPException(status_code=400, detail=f"Category '{category_data.name}' already exists")

    # Update category
    updated_category = await db.category.update(
        where={"id": category_id},
        data={"name": category_data.name if category_data.name else category.name}
    )

    # Update product associations (if provided)
    if category_data.product_ids is not None:
        for pid in category_data.product_ids:
            product = await db.product.find_unique(where={"id": pid})
            if not product:
                await db.disconnect()
                raise HTTPException(status_code=404, detail=f"Product with id {pid} not found")
            await db.product.update(
                where={"id": pid},
                data={"categoryId": category_id}
            )

    await db.disconnect()
    return updated_category


# --------------------
# Delete category
# --------------------
@router.delete("/delete/{category_id}", response_model=dict)
async def delete_category(category_id: int):
    await db.connect()
    category = await db.category.find_unique(where={"id": category_id})

    if not category:
        await db.disconnect()
        raise HTTPException(status_code=404, detail=f"Category not found with id {category_id}")

    await db.category.delete(where={"id": category_id})
    await db.disconnect()
    return {"message": f"Category with id {category_id} deleted successfully"}
