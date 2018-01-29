package com.serhii.engine;

import com.serhii.exception.UserDisconnectedException;
import com.serhii.service.AuthorizationService;
import com.serhii.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.serhii.service.MessageService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Component
@Scope("prototype")
public class Worker implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(Worker.class);
    private Socket socket;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AuthorizationService authorizationService;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

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
                logger.trace(user.getNickname() + ": " + message);
                message = bufferedReader.readLine();
            }
            exit(user, bufferedReader, printWriter);
        } catch (IOException | UserDisconnectedException e) {
            exit(user, bufferedReader, printWriter);
        }

    }

    private void exit(User user, BufferedReader bufferedReader, PrintWriter printWriter) {
        if (user == null) {
            logger.info("Client left the chat");
        } else if (!user.getNickname().equals("/exit")) {
            logger.info(user.getNickname() + " left the chat");
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
