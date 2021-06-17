package com.example.labelapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabellngJobApiApp {

    private static int count = 0;

    public static void main(String[] args) {
        SpringApplication.run(LabellngJobApiApp.class, args);
    }
}
