package ru.se.info.tinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LocationServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(LocationServiceApp.class, args);
    }

}
