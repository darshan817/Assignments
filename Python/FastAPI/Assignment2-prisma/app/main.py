from fastapi import FastAPI
from app.api.routes import companyRouter, productRouter, categoryRouter
from app.db.database import connect_db, disconnect_db

app = FastAPI()

app.include_router(companyRouter.router)
app.include_router(productRouter.router)
app.include_router(categoryRouter.router)

    
@app.on_event("startup")
async def on_startup():
    await connect_db()

@app.on_event("shutdown")
async def on_shutdown():
    await disconnect_db()
