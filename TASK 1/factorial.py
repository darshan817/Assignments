# recursive method for finding recursive of a given number n
def recursive(n):
    '''
    Recursion tree 
    recursive(5)
  └── recursive(4)
        └── recursive(3)
              └── recursive(2)
                    └── recursive(1)
                          └── recursive(0)

                          
    Stack memory allocation 

    +----------------------+
    | recursive(0)         | ← n = 0, returns 1
    +----------------------+
    | recursive(1)         | ← waiting for recursive(0)
    +----------------------+
    | recursive(2)         | ← waiting for recursive(1)
    +----------------------+
    | recursive(3)         | ← waiting for recursive(2)
    +----------------------+
    | recursive(4)         | ← waiting for recursive(3)
    +----------------------+
    | recursive(5)         | ← initial call, waiting for recursive(4)
    +----------------------+

    Heap memory allocation
    Heap (Object Storage):

    All values like:
    The integer 5
    The return value 120
    Any intermediate integer like n - 1
    

    Even though the objects are on the heap,
    the flow control, return addresses, and
    execution state of recursive calls must still be managed in LIFO order — that’s what the stack is perfect for
    
    '''
    a = int(n)  # Integer object stored in heap
    a = 5
    if n == 0 or n == 1:
        return 2
    return n * recursive(n-1)

# Iterative method for finding recursive of a given number n
def iterative(n):
    '''
    Stack memory allocation
    +--------------------------+
    | iterative(n=5)      |
    |--------------------------|
    | i → points to int object |
    | fact → points to int   |
    +--------------------------+

    
    Heap memory allocation
    [int 1] [int 2] [int 3] ... [int 120]
    (Actual integer objects stored here)

    '''
    fact = 1
    for i in range(2, n+1):
        fact *= i
    return fact

if __name__ == "__main__":
    n = int(input("Enter a number: "))
    # print(recursive(5))
    print(iterative(5))