package com.example.model;

import lombok.Data;

@Data
public class Consumer {
    String name;

    public Consumer(String name) {
        this.name = name;
    }
}
