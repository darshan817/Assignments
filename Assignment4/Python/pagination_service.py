import pandas as pd
from services.student_service import StudentService

class PaginationService:
    def __init__(self, student_service: StudentService):
        self.student_service = student_service

    def get_students(self, filters: dict = None, sort_by: str = None, ascending=True, start=0, end=None):
        if not self.student_service.students:
            return pd.DataFrame()

        # Convert students to DataFrame
        df = pd.DataFrame([s.dict() for s in self.student_service.students])

        # Apply filters
        if filters:
            for key, value in filters.items():
                if key in df.columns:
                    df = df[df[key] == value]

        # Apply sorting
        if sort_by and sort_by in df.columns:
            df = df.sort_values(by=sort_by, ascending=ascending)

        # Pagination
        if end is None or end > len(df):
            end = len(df)
        df_paginated = df.iloc[start:end]

        return df_paginated.reset_index(drop=True)
