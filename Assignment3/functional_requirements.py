import pandas as pd

class FunctionalRequirements:
    def __init__(self, service):
        self.add_service = service

    #  Generic filter function
    def _apply_filters(self, students, gender=None, age=None, class_id=None, city=None, pincode=None):
        filtered = students

        if gender:
            filtered = [s for s in filtered if s.gender == gender]
        if age:
            filtered = [s for s in filtered if s.age == age]
        if class_id:
            filtered = [s for s in filtered if s.class_id == class_id]

        if city:
            student_ids = [addr.student_id for addr in self.add_service.addresses if addr.city == city]
            filtered = [s for s in filtered if s.id in student_ids]

        if pincode:
            student_ids = [addr.student_id for addr in self.add_service.addresses if addr.pincode == pincode]
            filtered = [s for s in filtered if s.id in student_ids]

        return filtered

    #  1. Find Students by Pincode
    def students_by_pincode(self, pincode, **filters):
        students = [s for s in self.add_service.students
                    if any(s.id == addr.student_id for addr in self.add_service.addresses if addr.pincode == pincode)]
        students = self._apply_filters(students, **filters)
        return pd.DataFrame([s.model_dump() for s in students]) if students else pd.DataFrame()

    #  2. Find Students by City
    def students_by_city(self, city, **filters):
        students = [s for s in self.add_service.students
                    if any(s.id == addr.student_id for addr in self.add_service.addresses if addr.city == city)]
        students = self._apply_filters(students, **filters)
        return pd.DataFrame([s.model_dump() for s in students]) if students else pd.DataFrame()

    #  3. Find Students by Class
    def students_by_class(self, class_id, **filters):
        students = [s for s in self.add_service.students if s.class_id == class_id]
        students = self._apply_filters(students, **filters)
        return pd.DataFrame([s.model_dump() for s in students]) if students else pd.DataFrame()

    #  4. Get Passed Students
    def passed_students(self, passing_marks=50, **filters):
        students = [s for s in self.add_service.students if s.marks >= passing_marks]
        students = self._apply_filters(students, **filters)
        return pd.DataFrame([s.model_dump() for s in students]) if students else pd.DataFrame()

    #  5. Get Failed Students
    def failed_students(self, passing_marks=50, **filters):
        students = [s for s in self.add_service.students if s.marks < passing_marks]
        students = self._apply_filters(students, **filters)
        return pd.DataFrame([s.model_dump() for s in students]) if students else pd.DataFrame()

    #  6. Ranking
    def ranking(self, **filters):
        students = sorted(self.add_service.students, key=lambda s: s.marks, reverse=True)
        students = self._apply_filters(students, **filters)
        return pd.DataFrame([s.model_dump() for s in students]) if students else pd.DataFrame()
