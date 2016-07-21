package com.example.turlough.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by turlough on 14/07/16.
 */
public class ExecutorExample {

    //<editor-fold desc="main">
    public static void main(String[] args){
        ExecutorExample example = new ExecutorExample();
        example.setPoolSize(1);
        Runnable[] runnables = example.createRunnables("runnable", 20);
        example.execute(runnables);
    }
    //</editor-fold>

    //<editor-fold desc="public methods">

    LinkedBlockingQueue<Runnable> queue;
    ExecutorService executor;

    static int corePoolSize = 3;
    static int maxPoolSize = 6;
    long keepAliveTime = 10;


    public ExecutorExample() {

        queue = new LinkedBlockingQueue<>();

        executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                queue
        );

    }

    private void execute(Runnable[] runnables) {

        Arrays.asList(runnables).stream()
                .forEach((r) -> {executor.execute(r);});

        System.out.println("All jobs are queued for execution...");
    }


    public Runnable[] createRunnables(String name, int count){

        Runnable[] runnables = new Runnable[count];
        Random random = new Random();

        for (int i = 0; i < count; i++) {

            final int index = i;
            final long time = random.nextInt(2500);

            runnables[i] = () -> {

                long start = System.currentTimeMillis();

                System.out.printf("Starting job '%s %d'\n", name, index);
                sleep(time);

                long duration = System.currentTimeMillis() - start;
                long error = duration - time;

                System.out.printf("Job '%s %d':\t Expected %d ms, overran by %d ms\n", name, index, time, error);

            };
        }

        return runnables;
    }

    public List<Callable<Integer>> getStatistics(String name, int count){

        Runnable[] runnables = new Runnable[count];
        Random random = new Random();
        List<Integer> values = new ArrayList<>();
        List<Callable<Integer>> callables = new ArrayList<>(count);

        return callables;
    }

    //</editor-fold>

    //<editor-fold desc="private methods">
    private void sleep(long millis){
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        corePoolSize = maxPoolSize;
    }
    // </editor-fold>)




}
