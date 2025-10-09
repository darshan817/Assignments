package Assignment6;
import java.util.*;
import java.util.concurrent.*;
public class PerformanceComparision {
    public static void main(String[] args) throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) list.add(i);
        long start = System.currentTimeMillis();
        long sum1 = list.parallelStream().mapToLong(Integer::longValue).sum();
        long end = System.currentTimeMillis();
        System.out.println("ParallelStream → " + sum1 + " | Time: " + (end - start) + "ms");
        ExecutorService ex = Executors.newFixedThreadPool(4);
        int chunk = list.size() / 4;
        List<Future<Long>> parts = new ArrayList<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            int from = i * chunk;
            int to = (i == 3) ? list.size() : from + chunk;
            parts.add(ex.submit(() -> {
                long s = 0;
                for (int j = from; j < to; j++) s += list.get(j);
                return s;
            }));
        }
        long sum2 = 0;
        for (Future<Long> f : parts) sum2 += f.get();
        end = System.currentTimeMillis();
        ex.shutdown();
        System.out.println("ExecutorService → " + sum2 + " | Time: " + (end - start) + "ms");
    }
}
