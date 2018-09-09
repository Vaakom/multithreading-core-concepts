package core.multithreding.callablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Processor implements Callable<String> {

    private int id;

    public Processor(int id) {
        this.id = id;
    }

    public String call() throws Exception {
        Thread.sleep(3000);
        return "Id: " + id;
    }
}

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Future<String>> list = new ArrayList<Future<String>>();

        for (int i = 0; i < 5; i++) {
            Future<String> future = executorService.submit(new Processor(i));
            list.add(future);
        }

        for(Future<String> future : list){
            System.out.println("Getting future value...");
            System.out.println(future.get());
        }

        executorService.shutdown();
    }
}

