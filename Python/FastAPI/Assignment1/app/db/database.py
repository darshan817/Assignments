from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base

from core.config import settings
SQLALCHEMY_URL = settings.DATABASE_URL

engine = create_engine(SQLALCHEMY_URL)
# engine = create_engine(SQLALCHEMY_URL, echo=True, future=True)

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()