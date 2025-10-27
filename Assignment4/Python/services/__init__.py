import os
import json

class FileHandler:
    def __init__(self, folder='data'):
        self.folder = folder
        os.makedirs(self.folder, exist_ok=True)

    def save(self, filename, data_list):
        """Save a list of Pydantic objects to a JSON file."""
        filepath = os.path.join(self.folder, filename)
        with open(filepath, 'w') as f:
            json.dump([d.dict() for d in data_list], f, indent=4)

    def load(self, filename):
        """Load a JSON file into a list of dicts."""
        filepath = os.path.join(self.folder, filename)
        if not os.path.exists(filepath):
            return []
        with open(filepath, 'r') as f:
            return json.load(f)
