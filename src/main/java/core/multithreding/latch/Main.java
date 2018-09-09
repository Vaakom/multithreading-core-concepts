package core.multithreding.latch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Runnable {
    private int id;

    private CountDownLatch latch;


    public Worker(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    public void run() {
        doWork();
        latch.countDown();
    }

    private void doWork() {
        System.out.println("Thread start. id: " + (id + 1));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newSingleThreadExecutor();

        CountDownLatch latch = new CountDownLatch(5);
        for(int i=0; i< 5; i++)
            executor.execute(new Worker(i, latch));

        latch.await();

        System.out.println("Done!");
    }
}
