package com.serhii.config;

import com.serhii.runner.ChatServer;
import com.serhii.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
@PropertySource(
        value={"classpath:properties/${property:defaultValue}.properties"},
        ignoreResourceNotFound = true)
public class AppConfig {
    @Value("${chat.server.port:8080}")
    private int chatServerPort;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return new ChatServer();
    }

    @Bean
    public ServerSocket serverSocket() throws IOException {
        return new ServerSocket(chatServerPort);
    }

    @Bean
    public AuthorizationService authorizationService() {
        return new AuthorizationService();
    }

    @Bean
    public ChatServer chatServer() {
        return new ChatServer();
    }

}
