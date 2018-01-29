package com.serhii.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
public class AppConfig {

    @Bean
    public ServerSocket serverSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8082);
        return serverSocket;
    }
}
