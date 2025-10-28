class Product :
    def __init__(self, name, stock, price, location, tags):  
        self.name = name
        self.stock = stock
        self.price = price
        self.location = location
        self.tags = tags
    
    def __str__(self):
        return f"{self.name} | {self.stock} | {self.price} | {self.location} | {self.tags}" 
    

class FoodProduct(Product):
    def __init__(self, name, stock, price, location, tags, expiry):
        super().__init__(name, stock, price, location, tags)
        self.expiry = expiry

        def __str__(self):
            return f"{self.name} | {self.stock} | {self.price} | {self.location} | {self.tags} | {self.expiry}" 