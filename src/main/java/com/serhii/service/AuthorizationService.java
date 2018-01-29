package com.serhii.service;

import com.serhii.engine.Worker;
import com.serhii.exception.UserDisconnectedException;
import com.serhii.handler.PrintWriterHandler;
import com.serhii.handler.UserHandler;
import com.serhii.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class AuthorizationService {
    private static final Logger logger = LoggerFactory.getLogger(Worker.class);
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private PrintWriterHandler printWriterHandler;

    public User authorize(BufferedReader bufferedReader, PrintWriter printWriter)
            throws IOException, UserDisconnectedException {
        User user = null;
        printWriter.println("Welcome to chat, please login or register");
        while (user == null) {
            printWriter.println("Enter your login (nickname)");
            String nickname = bufferedReader.readLine();
            if (nickname != null && !nickname.equals("/exit")) {
                user = userHandler.getUser(nickname) != null ? login(nickname, bufferedReader,
                        printWriter) : registration(nickname, bufferedReader, printWriter);
            } else {
                throw new UserDisconnectedException("User disconnected");
            }
        }
        return user;
    }

    private User registration(String nickname, BufferedReader bufferedReader, PrintWriter printWriter)
            throws IOException {
        printWriter.println("Choose your password");
        String password = bufferedReader.readLine();
        printWriter.println("Confirm password");
        String confirm = bufferedReader.readLine();
        if (password.equals(confirm)) {
            User user = new User(nickname, password);
            userHandler.addUser(nickname, password);
            printWriter.println("Registration success");
            logger.info(nickname + " is connected");
            printWriterHandler.getMap().put(nickname, printWriter);
            return user;
        } else {
            printWriter.println("Registration failed, please try again");
            return null;
        }
    }

    private User login(String nickname, BufferedReader bufferedReader, PrintWriter printWriter)
            throws IOException {
        User user = null;
        if (!printWriterHandler.getMap().containsKey(nickname)) {
            printWriter.println("Enter your password");
            user = userHandler.getUser(nickname);
            if (user != null && user.getPassword().equals(bufferedReader.readLine())) {
                printWriter.println("Login success");
                logger.info(nickname + " is connected");
                printWriterHandler.getMap().put(nickname, printWriter);
            } else {
                printWriter.println("Login failed, please try again");
            }
        } else {
            printWriter.println("You're already logged in");
        }
        return user;
    }
}
