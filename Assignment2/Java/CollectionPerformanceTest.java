import java.util.*;

public class CollectionPerformanceTest {

    public static long testInsertion(List<Integer> list, int n) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            list.add(i);   // insert at end
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static long testDeletion(List<Integer> list, int n) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            list.remove(0);   // delete from beginning
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) {
        int[] sizes = {10000, 50000, 100000};

        for (int n : sizes) {
            System.out.println("\n=== Testing with " + n + " elements ===");

            // ArrayList
            List<Integer> arrayList = new ArrayList<>();
            long arrInsert = testInsertion(arrayList, n);

            arrayList = new ArrayList<>();
            for (int i = 0; i < n; i++) arrayList.add(i);
            long arrDelete = testDeletion(arrayList, n);

            System.out.println("ArrayList - Insert: " + arrInsert + " ms, Delete: " + arrDelete + " ms");

            // LinkedList
            List<Integer> linkedList = new LinkedList<>();
            long llInsert = testInsertion(linkedList, n);

            linkedList = new LinkedList<>();
            for (int i = 0; i < n; i++) linkedList.add(i);
            long llDelete = testDeletion(linkedList, n);

            System.out.println("LinkedList - Insert: " + llInsert + " ms, Delete: " + llDelete + " ms");
        }
    }
}
