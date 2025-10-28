def addProduct():
    global ALLProducts
    name, stock, price, location, tags = input("Enter product details : ").split(',') #seperated by ,
    # print(name)
    ALLProducts.append({
        'name': name,
        'stock': stock,
        'price': price,
        'location': location,
        'tags' : tags 
    })

def deleteProduct(productName):
    global ALLProducts
    for product in ALLProducts:
        if product['name'] == productName:
            ALLProducts.remove(product)
    return ALLProducts
    

def updateProduct(productName, **details):
    global ALLProducts
    for product in ALLProducts:
        
        if product['name'] == productName:
            for i in details.keys():
                product[i] = details[i]

def getAllProducts():
    global ALLProducts
    print("Name  |  Stock  |  Price  |  Location  |  Tags")
    for product in ALLProducts:
        print(f"{product['name']} | {product['stock']} | {product['price']} | {product['location']} | {product['tags']}")
    

def totalValue():
    global ALLProducts
    sum = 0
    for product in ALLProducts:
        sum += product['price']
    print(sum)

def discountedItems():
    global ALLProducts
    print("Discounted items are :- ")
    print("Name  |  Stock  |  Price  |  Location  |  Tags")
    for product in ALLProducts:
        if product['tags'] == 'clearance':
            print(f"{product['name']} | {product['stock']} | {product['price'] * (50/100)} | {product['location']} | {product['tags']}")

def lowStock():
    global ALLProducts
    for product in ALLProducts:
        if product['stock'] < 5 :
            print("warning {} has stock lower then 5.".format(product['name']))

def updateStock():
    global ALLProducts
    for product in ALLProducts:
        stock = input(f'Porvide current stock of {product['name']}')
        product['stock'] = stock

if __name__ == "__main__":        
    ALLProducts = [
        {'name': "icecream", 'stock':2, 'price':15000, 'location':"indore", 'tags':"grocery"},
        {'name': "chocolate", 'stock':2, 'price':1000, 'location':"ujjain", 'tags':"clearance"}
    ]

    while 1:
        n = int(input('''
1. List all products
2. Low on stock warnings. (LOW_STOCK = 5)
3. Add product.
4. Update stock.
5. Delete product.
6. Print Total value of all products in stock.
7. Apply Discount by Tag and print the discounted items details with new price (If tags: {"clearance"} apply discount of 50%)
0. Exit
Enter Your Choice : 
        '''))
        if n == 1:
            getAllProducts()
        elif n == 2:
            lowStock()
        elif n == 3:
            addProduct()
        elif n == 4:
            updateStock()
        elif n == 5:
            product = input('Name of the product you want to delete : ')
            deleteProduct(product)
        elif n == 6:
            totalValue()
        elif n == 7:
            discountedItems()
        elif n==0:
            break