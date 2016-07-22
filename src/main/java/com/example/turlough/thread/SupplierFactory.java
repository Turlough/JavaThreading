package com.example.turlough.thread;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by turlough on 11/07/16.
 */
public class SupplierFactory {


    private boolean logging;
    AtomicBoolean running = new AtomicBoolean(true);
    private final BlockingQueue<StringSupplier> publicQueue;


    public SupplierFactory(int maxSize) {
        publicQueue = new ArrayBlockingQueue<>(maxSize);
    }



    public Thread createConsumerThread(String name, int millis) throws InterruptedException {

        return new Thread(() -> {
            while (isRunning()){
                StringSupplier s = null;
                try {
                    s = publicQueue.take();
                    processMessage(name, s.get(), millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            System.out.printf("%s has stopped\n", name);

        });
    }



    public void schedule(BlockingQueue<StringSupplier> queue, int millis)  {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        int count = 0;

        while(! queue.isEmpty()){

            if(! isRunning()){
                List<Runnable> tasks = executor.shutdownNow();
                System.out.printf(
                        "Shut down the supplier with %d tasks undone and % d items in the queue",
                        tasks.size(),
                        queue.size()
                        );
                return;
            }

            executor.schedule(() -> {

                StringSupplier s = null;
                try {
                    s = queue.take();
                    put(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, millis * count++, TimeUnit.MILLISECONDS);
        }

    }

    public void scheduleAtFixedRate(int millis)  {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        //executor.scheduleWithFixedDelay(() -> System.out.println("Tick"), 0, millis, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(() -> System.out.println("Tick"), 0, millis, TimeUnit.MILLISECONDS);
    }


    public void scheduledTaskTimer(long millis) throws ExecutionException, InterruptedException {

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture future = service.schedule(()->{}, millis, TimeUnit.MILLISECONDS);
        future.get();

    }

    public Boolean isRunning() {
        return running.get();
    }

    public void setRunning(Boolean running) {
        this.running.set(running);
        System.out.println("Factory is " + (running ? "running" : "stopped" + "\n"));
    }

    private void processMessage(String name, String msg, int processingTime) {

            System.out.printf("%s consumed message \t '%s'. Processing, please wait...\n", name, msg);
            sleep(processingTime);
            System.out.printf("%s processed message\t '%s'.\n",name, msg);

    }


    private void shutdown(ExecutorService es) {
        es.shutdown();
        try {
            es.awaitTermination(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep(long millis){
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    private void log(String s) {
        if (logging)
            System.out.println(s);
    }

    private void put(StringSupplier supplier) {
        try {
            log(String.format("%s",supplier.get()));
            publicQueue.put(supplier);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
