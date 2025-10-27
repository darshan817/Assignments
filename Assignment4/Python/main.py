import sys
from services import FileHandler
from services.student_service import StudentService
from services.address_service import AddressService
from services.class_service import ClassService
from functional_requirements import FunctionalRequirements
from delete_operation import DeleteOperation
from pagination_service import PaginationService
import pandas as pd

# Initialize services
file_handler = FileHandler()
student_service = StudentService(file_handler)
address_service = AddressService(file_handler)
class_service = ClassService(file_handler)

# Initialize helpers
func_req = FunctionalRequirements(student_service, address_service)
deleter = DeleteOperation(student_service, address_service, class_service)
pagination = PaginationService(student_service)

# Helper function to print DataFrame nicely
def print_df(df: pd.DataFrame):
    if df.empty:
        print("No records found.")
    else:
        print(df.to_string(index=False))

# Menu-driven terminal interface
def main_menu():
    while True:
        print("\n--- Student Management System ---")
        print("1. Add Student")
        print("2. Add Address")
        print("3. Add Class")
        print("4. View All Students")
        print("5. View All Addresses")
        print("6. View All Classes")
        print("7. Find Students by City")
        print("8. Find Students by Pincode")
        print("9. Find Students by Class")
        print("10. Passed Students")
        print("11. Failed Students")
        print("12. Ranking")
        print("13. Delete Student")
        print("14. Paginate Students")
        print("15. Delete Address")
        print("16. Delete Class")
        print("0. Exit")

        choice = input("Enter your choice: ").strip()

        if choice == "1":
            try:
                id = int(input("Student ID: "))
                name = input("Name: ")
                class_id = int(input("Class ID: "))
                marks = float(input("Marks: "))
                gender = input("Gender (M/F): ")
                age = int(input("Age: "))
                student_service.add_student(id=id, name=name, class_id=class_id, marks=marks, gender=gender, age=age)
            except ValueError:
                print("Invalid input. Try again.")
        elif choice == "2":
            try:
                id = int(input("Address ID: "))
                student_id = int(input("Student ID: "))
                city = input("City: ")
                pincode = input("Pincode: ")
                address_service.add_address(id=id, student_id=student_id, city=city, pincode=pincode)
            except ValueError:
                print("Invalid input. Try again.")
        elif choice == "3":
            try:
                id = int(input("Class ID: "))
                name = input("Class Name: ")
                class_service.add_class(id=id, name=name)
            except ValueError:
                print("Invalid input. Try again.")
        elif choice == "4":
            student_service.get_students()
        elif choice == "5":
            address_service.get_addresses()
        elif choice == "6":
            class_service.get_classes()
        elif choice == "7":
            city = input("Enter city: ")
            df = func_req.students_by_city(city)
            print_df(df)
        elif choice == "8":
            pincode = input("Enter pincode: ")
            df = func_req.students_by_pincode(pincode)
            print_df(df)
        elif choice == "9":
            try:
                class_id = int(input("Enter Class ID: "))
                df = func_req.students_by_class(class_id)
                print_df(df)
            except ValueError:
                print("Invalid input.")
        elif choice == "10":
            try:
                passing_marks = float(input("Enter passing marks (default 50): ") or 50)
                df = func_req.passed_students(passing_marks=passing_marks)
                print_df(df)
            except ValueError:
                print("Invalid input.")
        elif choice == "11":
            try:
                passing_marks = float(input("Enter passing marks (default 50): ") or 50)
                df = func_req.failed_students(passing_marks=passing_marks)
                print_df(df)
            except ValueError:
                print("Invalid input.")
        elif choice == "12":
            df = func_req.ranking()
            print_df(df)
        elif choice == "13":
            try:
                student_id = int(input("Enter Student ID to delete: "))
                deleter.delete_student(student_id)
            except ValueError:
                print("Invalid input.")
        elif choice == "14":
            start = int(input("Start index: ") or 0)
            end_input = input("End index: ")
            end = int(end_input) if end_input else None
            filters_input = input("Filters (key=value, comma separated, e.g., gender=F, class_id=101): ").strip()
            filters = {}
            if filters_input:
                for item in filters_input.split(","):
                    k, v = item.split("=")
                    # Convert numeric filters
                    if k.strip() in ["id", "class_id", "age"]:
                        v = int(v.strip())
                    elif k.strip() == "marks":
                        v = float(v.strip())
                    else:
                        v = v.strip()
                    filters[k.strip()] = v
            sort_by = input("Sort by column (leave blank for no sorting): ").strip() or None
            asc_input = input("Ascending? (Y/N, default Y): ").strip().upper() or "Y"
            ascending = True if asc_input == "Y" else False
            df = pagination.get_students(filters=filters, sort_by=sort_by, ascending=ascending, start=start, end=end)
            print_df(df)
        elif choice == "15":
            try:
                address_id = int(input("Enter Address ID to delete: "))
                deleter.delete_address(address_id)
            except ValueError:
                print("Invalid input.")
        elif choice == "16":
            try:
                class_id = int(input("Enter Class ID to delete: "))
                deleter.delete_class(class_id)
            except ValueError:
                print("Invalid input.")
        elif choice == "0":
            print("Exiting...")
            sys.exit()
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main_menu()
