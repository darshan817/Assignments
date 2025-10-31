from app.db.generated import Prisma
# from dotenv import load_dotenv
import os

DATABASE_URL = os.getenv("DATABASE_URL")
print("Connecting to database:", DATABASE_URL)

db = Prisma()

async def connect_db():
    if not db.is_connected():
        print("Connecting DB…")
        await db.connect()
        print("✅ Database connected")   

async def disconnect_db():
    if db.is_connected():
        await db.disconnect()
        print("❌ Database disconnected")