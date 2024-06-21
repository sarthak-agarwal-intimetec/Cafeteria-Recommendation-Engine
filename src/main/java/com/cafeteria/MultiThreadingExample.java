package src.main.java.com.cafeteria;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadingExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> {
            // Task for inserting data into the database
        };

        Runnable task2 = () -> {
            // Task for generating reports
        };

        executor.submit(task1);
        executor.submit(task2);

        executor.shutdown();
    }
}
