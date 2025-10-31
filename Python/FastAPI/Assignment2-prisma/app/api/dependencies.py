from app.db.database import db

async def get_db():
    try:
        # Connect to Prisma client before using
        if not db.is_connected():
            await db.connect()
        yield db
    finally:
        # Optionally disconnect after request
        await db.disconnect()