package com.example.turlough.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by turlough on 11/07/16.
 */
public class Main {



    public static void main(String [] args){

        SupplierFactory factory = new SupplierFactory(1000);
        factory.setLogging(false);

        new Main().start(factory);
    }

    void start(SupplierFactory factory){

        factory.setRunning(true);

        try {

            BlockingQueue<StringSupplier> queue1 = createStringSupplierQueue("supplier 1", 10);
            factory.schedule(queue1, 2000);
            BlockingQueue<StringSupplier> queue2 = createStringSupplierQueue("supplier 2", 20);
            factory.schedule(queue2, 500);


            Thread t1 = factory.createConsumerThread("consumer 1", 1000);
            Thread t2 = factory.createConsumerThread("consumer 2", 1500);
            Thread t3 = factory.createConsumerThread("consumer 3", 1200);

            t1.start();
            t2.start();
            t3.start();

            //sleepWithTimerThread(10000);
            sleepWithLatch( () -> {
                System.out.println("Callback called");
                factory.setRunning(false);
            }, 30000);

            System.out.println("Done");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleepWithLatch(CompletionListener listener, int millis) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        setAlarm(() ->{
            latch.countDown();
            listener.onCompleted();

        }, millis);

        latch.await();
    }

    private void sleepWithTimerThread(int millis) throws InterruptedException {
        Thread timer = new Thread(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timer.start();
        timer.join();
    }


    public BlockingQueue<StringSupplier> createStringSupplierQueue(String name, int count) throws InterruptedException {

        BlockingQueue<StringSupplier> queue = new ArrayBlockingQueue<>(count);
        for (int i = 0; i < count; i++) {
            queue.put(new StringSupplier(name + " supplied item " + i));
        }
        return queue;
    }

    public void setAlarm(CompletionListener listener, long millis){
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(millis);
                listener.onCompleted();
            } catch (InterruptedException e) {
                e.printStackTrace();
                listener.onCompleted();
            }
        }).start();
    }
}
