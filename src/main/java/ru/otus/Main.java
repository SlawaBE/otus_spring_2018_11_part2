package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Locale;

@SpringBootApplication
@EnableMongoRepositories
public class Main {

    public static void main(String[] args) {
//        Console.main(args);
        ApplicationContext context = SpringApplication.run(Main.class);
        System.out.println(Locale.getDefault());
    }
}
