from fastapi import FastAPI, Depends, APIRouter
from sqlalchemy.orm import Session
from sqlalchemy import inspect
from api.routes import companyRouter, productRouter, categoryRouter
import models
from schemas.CompanySchema import CompanyCreate, CompanyResponse
from db.database import engine, SessionLocal, Base
from typing import List
from api.dependencies import get_db

Base.metadata.create_all(bind=engine)

app = FastAPI()

app.include_router(companyRouter.router)
app.include_router(productRouter.router)
app.include_router(categoryRouter.router)

    

