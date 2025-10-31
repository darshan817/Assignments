from fastapi import FastAPI
from app.api.routes import fileRouter
from app.db.database import connect_db, disconnect_db

app = FastAPI()

    
@app.on_event("startup")
async def on_startup():
    await connect_db()

@app.on_event("shutdown")
async def on_shutdown():
    await disconnect_db()

app.include_router(fileRouter.router)
