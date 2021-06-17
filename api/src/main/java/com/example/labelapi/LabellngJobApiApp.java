package com.example.labelapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class LabellngJobApiApp {

    private static int count = 0;

    public static void main(String[] args) {
        SpringApplication.run(LabellngJobApiApp.class, args);
    }

    @Bean
    public Consumer<String> log() {
        return payload -> {
            System.out.println("Static consumer called ");
            System.out.println("Message: " + payload + " delivered");
        };
    }

    @Bean
    public Consumer<String> pnt() {
        return payload -> {
            System.out.println("Dynamic consumer called");
            System.out.println("Message: " + payload + " delivered");
        };
    }

    //@Bean
    public Supplier<Integer> send() {
        System.out.println("Supplier");
        return () -> (int) (Math.random() * 10);
    }

    @Bean
    public Function<Integer, Integer> process() {
        System.out.println("Processor");
        return count -> (count * count) % 1000;
    }

    public static class Person {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}
