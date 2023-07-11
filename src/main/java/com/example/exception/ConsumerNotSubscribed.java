package com.example.exception;

public class ConsumerNotSubscribed extends Throwable {
    public ConsumerNotSubscribed(String message) {
        super(message);
    }
}
