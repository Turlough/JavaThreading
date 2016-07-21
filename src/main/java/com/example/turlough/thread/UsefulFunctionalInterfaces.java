package com.example.turlough.thread;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by turlough on 21/07/16.
 */
public class UsefulFunctionalInterfaces {

    //no return value, no parameters
    Runnable runnable = () -> System.out.println("Runnable ran");

    //a return value, no parameters
    Callable<Integer> get5 = (Callable) () -> 5;

    //no return value, one parameter
    Consumer <Integer> numberPrinter = (num) -> System.out.printf("You submitted %d\n", num);

    //a return value, and a parameter
    Function<Integer, Integer> doubler = (in) -> in * 2;

}
