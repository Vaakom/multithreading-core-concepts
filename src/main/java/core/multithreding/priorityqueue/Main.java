package core.multithreding.priorityqueue;

import java.util.concurrent.*;

class FirstWorker implements Runnable {

    private BlockingQueue<String> queue;

    public FirstWorker(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            queue.put("F");
            queue.put("B");
            queue.put("A");
            Thread.sleep(1000);
            queue.put("C");
            Thread.sleep(1000);
            queue.put("D");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SecondWorker implements Runnable {

    private BlockingQueue<String> queue;

    public SecondWorker(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println(queue.take());
            Thread.sleep(1000);
            System.out.println(queue.take());
            Thread.sleep(1000);
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}




public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new PriorityBlockingQueue<String>();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(new FirstWorker(queue));
        executor.execute(new SecondWorker(queue));

        executor.shutdown();


    }
}
