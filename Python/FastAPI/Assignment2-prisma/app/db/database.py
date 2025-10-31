from app.db.generated import Prisma
# from dotenv import load_dotenv
import os

DATABASE_URL = os.getenv("DATABASE_URL")
print("Connecting to database:", DATABASE_URL)

db = Prisma()

async def connect_db():
    await db.connect()

async def disconnect_db():
    await db.disconnect()