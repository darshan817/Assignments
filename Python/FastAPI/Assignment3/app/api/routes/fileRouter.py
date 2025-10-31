from fastapi import APIRouter, UploadFile, File, HTTPException, Depends, Query
from app.db.database import db
from app.schemas.RecordSchema import RecordCreate, RecordResponse
from typing import List
import pandas as pd
import io

router = APIRouter(prefix="/files", tags=["File Handling"])

# --------------------------
# Upload CSV → DB
# --------------------------
@router.post("/upload")
async def upload_csv(file: UploadFile = File(...)):
    if not file.filename.endswith(".csv"):
        raise HTTPException(status_code=400, detail="Only CSV files are allowed")

    content = await file.read()
    df = pd.read_csv(io.BytesIO(content))

    required_cols = {"name", "email", "age", "country"}
    if not required_cols.issubset(df.columns):
        raise HTTPException(status_code=400, detail=f"Missing columns: {required_cols - set(df.columns)}")

    for _, row in df.iterrows():
        await db.record.create(
            data={
                "name": row["name"],
                "email": row["email"],
                "age": int(row["age"]),
                "country": row["country"]
            }
        )

    return {"message": f"✅ Uploaded {len(df)} records successfully"}


# --------------------------
# Add manually
# --------------------------
@router.post("/add", response_model=RecordResponse)
async def add_record(record: RecordCreate):
    new_record = await db.record.create(data=record.dict())
    return new_record


# --------------------------
# Export data to CSV/XLS/XLSX
# --------------------------
@router.get("/export")
async def export_data(format: str = Query("csv", enum=["csv", "xls", "xlsx"]), limit: int = Query(None)):
    records = await db.record.find_many()
    if limit:
        records = records[:limit]

    df = pd.DataFrame(records)
    if df.empty:
        raise HTTPException(status_code=404, detail="No records found")

    if format == "csv":
        output = io.StringIO()
        df.to_csv(output, index=False)
        media_type = "text/csv"
        filename = "data.csv"
        content = output.getvalue()
    else:
        output = io.BytesIO()
        df.to_excel(output, index=False, engine="openpyxl")
        media_type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        filename = f"data.{format}"
        content = output.getvalue()

    from fastapi.responses import Response
    return Response(
        content=content,
        media_type=media_type,
        headers={"Content-Disposition": f"attachment; filename={filename}"}
    )
