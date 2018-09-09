package core.multithreding.producerconsumer;

import java.util.ArrayList;
import java.util.List;

class Processor {

    private final int MAX = 5;

    private final int MIN = 0;

    private final Object lock = new Object();

    private List<Integer> list = new ArrayList();

    private int value = 0;


    public void produce() throws InterruptedException {
        synchronized (lock){
            while(true){
                if(list.size() == MAX) {
                    System.out.println("Full. Waiting for consumer");
                    lock.wait();
                } else {
                    System.out.println("Adding " + ++value);
                    list.add(value);
                    lock.notify();
                }

                Thread.sleep(200);
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock){
            while(true){
                if(list.size() == MIN) {
                    System.out.println("Empty. Waiting for producer");
                    lock.wait();
                } else {
                    System.out.println("Consuming " + value--);
                    list.remove(list.size()-1);
                    lock.notify();
                }

                Thread.sleep(500);
            }
        }
    }
}
