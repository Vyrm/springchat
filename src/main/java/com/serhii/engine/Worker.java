package com.serhii.engine;

import com.serhii.exception.UserDisconnectedException;
import com.serhii.model.User;
import com.serhii.service.AuthorizationService;
import com.serhii.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private final Socket socket;
    private final MessageService messageService;
    private final AuthorizationService authorizationService;

    public Worker(Socket socket, MessageService messageService, AuthorizationService authorizationService) {
        this.socket = socket;
        this.messageService = messageService;
        this.authorizationService = authorizationService;
    }

    @Override
    public void run() {
        User user = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            user = authorizationService.authorize(bufferedReader, printWriter);
            messageService.sendUserLogin(user);
            messageService.lastMessages(user);
            String message = bufferedReader.readLine();
            while (message != null && !message.equals("/exit")) {
                messageService.send(message, user);
                LOGGER.trace(user.getNickname() + ": " + message);
                message = bufferedReader.readLine();
            }
            exit(user, bufferedReader, printWriter);
        } catch (IOException | UserDisconnectedException e) {
            exit(user, bufferedReader, printWriter);
        }
    }

    private void exit(User user, BufferedReader bufferedReader, PrintWriter printWriter) {
        if (user == null) {
            LOGGER.info("Client left the chat");
        } else if (!user.getNickname().equals("/exit")) {
            LOGGER.info(user.getNickname() + " left the chat");
            messageService.sendUserExit(user);
        }
        try {
            socket.close();
            bufferedReader.close();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
