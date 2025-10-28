from Services.ProductService import *
if __name__ == "__main__":        
    ALLProducts = [
        # {'name': "icecream", 'stock':2, 'price':15000, 'location':"indore", 'tags':"grocery"},
        # {'name': "chocolate", 'stock':2, 'price':1000, 'location':"ujjain", 'tags':"clearance"}
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
8. Stats
0. Exit
Enter Your Choice : 
        '''))
        if n == 1:
            listAllProducts(ALLProducts)
        elif n == 2:
            lowStock(ALLProducts)
        elif n == 3:
            prod = addProduct() 
            if prod:
                ALLProducts.append(prod) 
        elif n == 4:
            updateStock(ALLProducts)
        elif n == 5:
            deleteProduct(ALLProducts)
        elif n == 6:
            totalValue(ALLProducts)
        elif n == 7:
            discount(ALLProducts)
        elif n == 8 :
            stats(ALLProducts)
        elif n==0:
            break