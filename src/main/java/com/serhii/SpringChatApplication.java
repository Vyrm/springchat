package com.serhii;

import com.serhii.runner.ChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringChatApplication {
    @Autowired
    private ChatServer chatServer;

    public static void main(String[] args) {
        SpringApplication.run(SpringChatApplication.class, args);
    }

}
