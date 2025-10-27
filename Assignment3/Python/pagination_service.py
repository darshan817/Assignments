import pandas as pd

class PaginationService:
    def __init__(self, service):
        self.service = service

    def get_students(self, filters: dict = None, sort_by: str = None, ascending=True, start=0, end=None):
        if not self.service.students:
            return pd.DataFrame()

        df = pd.DataFrame([s.model_dump() for s in self.service.students])

        # Apply filters
        if filters:
            for key, value in filters.items():
                df = df[df[key] == value]

        # Apply sorting
        if sort_by:
            df = df.sort_values(by=sort_by, ascending=ascending)

        # Pagination
        if end is None:
            end = len(df)
        df_paginated = df.iloc[start:end]

        return df_paginated.reset_index(drop=True)
