package com.serhii.runner;

import com.serhii.SpringChatApplication;
import com.serhii.engine.Worker;
import com.serhii.service.AuthorizationService;
import com.serhii.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringChatApplication.class);

    @Autowired
    private ServerSocket serverSocket;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public void run(String... strings) throws Exception {
        LOGGER.info("Server is running @ " + serverSocket.getLocalSocketAddress());
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Worker(socket, messageService, authorizationService).start();
            } catch (IOException e) {
                LOGGER.error("An error occurred while accepting connection!");
                break;
            }
        }
    }
}
