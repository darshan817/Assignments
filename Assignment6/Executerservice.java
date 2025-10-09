package Assignment6;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class Executerservice {
    
    static class LineCountTask implements Callable<Integer>{
        private final Path filepath;
        
        public LineCountTask(Path filepath){
            this.filepath = filepath;
        }

        public Integer call(){
            int linecount = 0;
            try (BufferedReader reader = Files.newBufferedReader(filepath)){
                while (reader.readLine() != null) {
                    linecount++;
                }
            }
            catch (Exception e){

            }
            return linecount;
        }
    }
    

    public static void main(String[] args) {
        String directorypath = "C:\\Users\\vdars\\OneDrive\\Desktop\\Assignments\\Assignment6";
         
        int poolSize = 4;
        ExecutorService executer = Executors.newFixedThreadPool(poolSize);

        try {
            List<Future<Integer>> futures = new ArrayList<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directorypath), "*.txt")){
                for (Path filepath : stream){
                    Callable<Integer> task = new LineCountTask(filepath);
                    Future<Integer> future = executer.submit(task);
                    futures.add(future);
                }
            } catch (Exception e) {
                System.out.println("Directory not found");
                return;
            }

            int totallines = 0;
            int count = 1;
            
            for (Future<Integer> future : futures){
                try{
                    int line = future.get();
                    System.out.println("No. of Lines in file"+count+" "+line);
                    totallines += line;
                } catch (InterruptedException | ExecutionException e){
                    System.out.println("Error getting result");
                }
            }
            System.out.println("Total number of lines : "+ totallines);
        } finally {
            executer.shutdown();
        }
        
    }
}

