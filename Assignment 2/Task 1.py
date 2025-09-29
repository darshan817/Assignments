import time

# ---------------- Array Implementation ---------------- #
class Array:
    def __init__(self, size):
        self.size = size
        self.data = []
    
    def insert(self, value):
        self.data.append(value)   # insert at end
    
    def delete(self, index):
        if 0 <= index < len(self.data):
            self.data.pop(index)  # delete at given index

# ---------------- Linked List Implementation ---------------- #
class Node:
    def __init__(self, value):
        self.value = value
        self.next = None

class LinkedList:
    def __init__(self):
        self.head = None

    def insert(self, value):
        new_node = Node(value)
        if not self.head:
            self.head = new_node
        else:
            curr = self.head
            while curr.next:
                curr = curr.next
            curr.next = new_node

    def delete(self, index):
        if not self.head:
            return
        if index == 0:  # delete head
            self.head = self.head.next
            return
        curr = self.head
        prev = None
        count = 0
        while curr and count < index:
            prev = curr
            curr = curr.next
            count += 1
        if curr:
            prev.next = curr.next

# ---------------- Performance Test ---------------- #
def test_insertion(ds_class, n):
    ds = ds_class(n) if ds_class == Array else ds_class()
    start = time.time()
    for i in range(n):
        ds.insert(i)
    end = time.time()
    return (end - start) * 1000  # ms

def test_deletion(ds_class, n):
    ds = ds_class(n) if ds_class == Array else ds_class()
    for i in range(n):
        ds.insert(i)

    start = time.time()
    for i in range(n):
        ds.delete(0)  # delete from beginning
    end = time.time()
    return (end - start) * 1000  # ms

# ---------------- Main ---------------- #
if __name__ == "__main__":
    sizes = [10000, 50000, 100000]

    for n in sizes:
        print(f"\n=== Testing {n} elements ===")

        arr_insert = test_insertion(Array, n)
        arr_delete = test_deletion(Array, n)
        print(f"Array - Insert: {arr_insert:.2f} ms, Delete: {arr_delete:.2f} ms")

        ll_insert = test_insertion(LinkedList, n)
        ll_delete = test_deletion(LinkedList, n)
        print(f"LinkedList - Insert: {ll_insert:.2f} ms, Delete: {ll_delete:.2f} ms")
