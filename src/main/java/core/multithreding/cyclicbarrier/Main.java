package core.multithreding.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Runnable {

    private int id;

    private CyclicBarrier barrier;

    private Random random;

    public Worker(int id, CyclicBarrier barrier) {
        this.id = id;
        this.barrier = barrier;
        this.random = new Random();
    }

    public void run() {
        System.out.println("Thread start. id: " + (id + 1));
        doWork();
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Thread end. id: " + (id + 1));
    }

    private void doWork() {

        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int getId() {
        return id;
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
            public void run() {
                System.out.println("All tasks are finished");
            }
        });

        for (int i = 0; i < 5; i++)
            executor.execute(new Worker(i, barrier));

        executor.shutdown();


    }
}
