import numpy as np

from Models.Product import Product, FoodProduct

def listAllProducts(Allproducts):
    print("List of all product : ")
    print("Name | Stock | Price | Location | Tags | Expiry")
    for product in Allproducts:
        print(product)

def lowStock(Allproducts):
    print("Items with low stocks : ")
    for product in Allproducts:
        if product.stock < 5:
            print(product)

def updateStock(Allproducts):
    name = input("Provide name of the product you want to update stock of : ")
    for product in Allproducts:
        if product.name == name:
            stock = input(f'Porvide current stock of {product.name} : ')
            product.stock = stock

def deleteProduct(Allproducts):
    name = input("Name the product you want to delete : ")
    for product in Allproducts:
        if product.name == name:
            Allproducts.remove(product)
            break

def totalValue(Allproducts):
    sum = 0
    for product in Allproducts:
        sum += product.price
    print("Total value : ", sum)

def discount(Allproducts):
    print("Items with stock clearance sale : - ")
    for product in Allproducts:
        if 'clearance' in product.tags:
            price  = product.price
            product.price = product.price * (50/100)
            print(product)
            product.price = price
    

def addProduct():
    print("Enter product details : ")
    name = input('name : ')
    stock = int(input('stock : '))
    price = int(input('price : '))
    location = input('location : ')
    tags = set([x.strip() for x in input('tags "{clearance, grocery}": ').split(',')])
    expiry = input("expiry(if any) : ")

    if len(expiry) < 1:
        return Product(name, int(stock), int(price), location, tags)
    else :
        return FoodProduct(name, int(stock), int(price), location, tags, expiry)

# import numpy as np

def stats(Allproduct):
    if not Allproduct:
        print("No products found. Please add some first.")
        return
    price_list = [p.price for p in Allproduct]
    stock_list = [p.stock for p in Allproduct]
    inventory_values = [p.price * p.stock for p in Allproduct]

    clearance_prices = [p.price for p in Allproduct if 'clearance' in p.tags ]
    clearance_values = [p.price * p.stock for p in Allproduct if 'clearance' in p.tags ]

    print("Inventory Statistics:")
    print(f"- Average price of items: {np.average(price_list):.2f}")
    print(f"- Most expensive item price: {np.max(price_list):.2f}")
    print(f"- Total stock count: {np.sum(stock_list)}")
    print(f"- Total inventory value: {np.sum(inventory_values):.2f}")

    print("\nClearance products only:")
    if clearance_prices:
        print(f"- Average price (clearance): {np.average(clearance_prices):.2f}")
        print(f"- Total inventory value (clearance): {np.sum(clearance_values):.2f}")
    else:
        print("No clearance-tagged products found.")
