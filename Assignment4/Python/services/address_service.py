from pydantic import ValidationError
from models import Address
import pandas as pd
from . import FileHandler

class AddressService:
    def __init__(self, file_handler: FileHandler):
        self.file_handler = file_handler
        self.filename = 'address_data.json'
        self.addresses = [Address(**a) for a in self.file_handler.load(self.filename)]

    def add_address(self, **address_data):
        try:
            address = Address(**address_data)
            self.addresses.append(address)
            self.file_handler.save(self.filename, self.addresses)
        except ValidationError as e:
            print("Validation Error while adding address:", e.errors())
        except Exception as e:
            print("Unexpected Error while adding address:", e)

    def get_addresses(self):
        if not self.addresses:
            print("No addresses found.")
            return
        df = pd.DataFrame([a.dict() for a in self.addresses])
        print("\nAddresses Table:\n", df)
