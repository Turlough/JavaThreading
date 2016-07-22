package com.example.turlough.thread;

import java.util.function.Supplier;

/**
 * Created by turlough on 11/07/16.
 */
public class StringSupplier implements Supplier<String> {

    private String message;

    public StringSupplier(String message) {
        this.message = message;
    }

    @Override
    public String get() {

        return message;
    }
}
