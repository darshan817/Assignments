from pydantic_settings import BaseSettings, SettingsConfigDict

class Settings(BaseSettings):
    PROJECT_NAME : str = "MyFastAPIProject"
    DATABASE_URL: str = None

    model_config = SettingsConfigDict(env_file=".env", env_file_encoding="utf-8", from_attributes=True)

settings = Settings()
# print(settings.PROJECT_NAME)
# print(settings.DATABASE_URL)