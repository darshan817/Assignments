from fastapi import APIRouter, Depends, HTTPException
from typing import List
from app.api.dependencies import get_db
from app.schemas.ProductSchema import ProductCreate, ProductResponse, ProductUpdate

router = APIRouter(
    prefix="/products",
    tags=["Products"]
)


# --------------------
# Create new product
# --------------------
@router.post("/create", response_model=ProductResponse)
async def create_product(product: ProductCreate, db=Depends(get_db)):
    # ✅ Check if product with same name exists
    existing = await db.product.find_first(where={"name": product.name})
    if existing:
        raise HTTPException(
            status_code=400,
            detail=f"Product with name '{product.name}' already exists"
        )

    # ✅ Check company exists
    company = await db.company.find_unique(where={"id": product.company_id})
    if not company:
        raise HTTPException(
            status_code=404,
            detail=f"Company with id {product.company_id} not found"
        )

    # ✅ Check category exists (if provided)
    if product.category_id:
        category = await db.category.find_unique(where={"id": product.category_id})
        if not category:
            raise HTTPException(
                status_code=404,
                detail=f"Category with id {product.category_id} not found"
            )

    # ✅ Create new product (Prisma uses camelCase)
    new_product = await db.product.create(
        data={
            "name": product.name,
            "price": product.price,
            "companyId": product.company_id,
            "categoryId": product.category_id,
        }
    )

    print(f"✅ Created product with id {new_product.id}")
    return new_product



# --------------------
# Get all products
# --------------------
@router.get("/getAll", response_model=List[ProductResponse])
async def get_all_products(db=Depends(get_db)):
    products = await db.product.find_many()
    print(f"Retrieved {len(products)} products")
    return products


# --------------------
# Get product by ID
# --------------------
@router.get("/getByID/{product_id}", response_model=ProductResponse)
async def get_product_by_id(product_id: int, db=Depends(get_db)):
    product = await db.product.find_unique(where={"id": product_id})
    if not product:
        raise HTTPException(status_code=404, detail=f"Product not found with id {product_id}")
    return product


# --------------------
# Update product
# --------------------
# --------------------
# Update product (Partial update support)
# --------------------
@router.put("/update/{product_id}", response_model=ProductResponse)
async def update_product(product_id: int, product_data: ProductUpdate, db=Depends(get_db)):
    existing_product = await db.product.find_unique(where={"id": product_id})
    if not existing_product:
        raise HTTPException(status_code=404, detail=f"Product not found with id {product_id}")

    # ✅ Prepare update data dictionary only with provided fields
    update_data = {}
    if product_data.name is not None:
        update_data["name"] = product_data.name
    if product_data.price is not None:
        update_data["price"] = product_data.price
    if product_data.company_id is not None:
        update_data["companyId"] = product_data.company_id
    if product_data.category_id is not None:
        update_data["categoryId"] = product_data.category_id

    # ✅ Prevent duplicate name conflicts
    if "name" in update_data:
        duplicate = await db.product.find_first(where={"name": update_data["name"]})
        if duplicate and duplicate.id != product_id:
            raise HTTPException(
                status_code=400,
                detail=f"Another product with name '{update_data['name']}' already exists"
            )

    # ✅ Validate related entities if IDs provided
    if "companyId" in update_data:
        company = await db.company.find_unique(where={"id": update_data["companyId"]})
        if not company:
            raise HTTPException(
                status_code=404,
                detail=f"Company with id {update_data['companyId']} not found"
            )

    if "categoryId" in update_data:
        category = await db.category.find_unique(where={"id": update_data["categoryId"]})
        if not category:
            raise HTTPException(
                status_code=404,
                detail=f"Category with id {update_data['categoryId']} not found"
            )

    # ✅ Apply partial update
    updated_product = await db.product.update(
        where={"id": product_id},
        data=update_data
    )

    print(f"✅ Updated product with id {updated_product.id}")
    return updated_product

# --------------------
# Delete product
# --------------------
@router.delete("/delete/{product_id}", response_model=dict)
async def delete_product(product_id: int, db=Depends(get_db)):
    product = await db.product.find_unique(where={"id": product_id})
    if not product:
        raise HTTPException(status_code=404, detail=f"Product not found with id {product_id}")

    await db.product.delete(where={"id": product_id})
    print(f"Deleted product with id {product_id}")
    return {"message": f"Product with id {product_id} deleted successfully"}
