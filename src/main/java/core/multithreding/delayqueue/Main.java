package core.multithreding.delayqueue;

import java.util.concurrent.*;

class DelayedWorker implements Delayed {
    private long duration;

    private String message;

    private Long currentTimeMillis = System.currentTimeMillis();

    public DelayedWorker(long duration, String message) {
        this.duration = duration + System.currentTimeMillis();
        this.message = message;
    }

    public long getDelay(TimeUnit unit) {
            return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public int compareTo(Delayed o) {
        DelayedWorker worker = (DelayedWorker) o;
        if(this.duration < worker.getDuration())
            return -1;

        if(this.duration > worker.getDuration())
            return +1;

        return 0;
    }

    public long getDuration() {
        return duration;
    }

    public String getMessage() {
        return message;
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<DelayedWorker> queue = new DelayQueue<DelayedWorker>();
        queue.put(new DelayedWorker(1000, "First"));
        queue.put(new DelayedWorker(5000, "Second"));
        queue.put(new DelayedWorker(4000, "Third"));

        while(!queue.isEmpty())
            System.out.println(queue.take().getMessage());



    }
}
