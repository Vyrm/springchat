package com.serhii.service;

import com.serhii.handler.MessageHandler;
import com.serhii.handler.PrintWriterHandler;
import com.serhii.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class MessageService {

    @Autowired
    private PrintWriterHandler printWriterHandler;
    @Autowired
    private MessageHandler messageHandler;


    public void send(String message, User excludedUser) {
        for (String nickname : printWriterHandler.getMap().keySet()) {
            if (!nickname.equals(excludedUser.getNickname())) {
                printWriterHandler.getMap().get(nickname).println(excludedUser.getNickname()
                        + ": " + message);
            }
        }
        addMessageToMessageHandler(message, excludedUser);
    }

    public void sendUserLogin(User excludedUser) {
        for (String nickname : printWriterHandler.getMap().keySet()) {
            if (!nickname.equals(excludedUser.getNickname())) {
                printWriterHandler.getMap().get(nickname).println(excludedUser.getNickname()
                        + " is connected.");
            }
        }
    }

    public void sendUserExit(User excludedUser) {
        for (String nickname : printWriterHandler.getMap().keySet()) {
            if (!nickname.equals(excludedUser.getNickname())) {
                printWriterHandler.getMap().get(nickname).println(excludedUser.getNickname()
                        + " left the chat.");
            }
        }
        printWriterHandler.getMap().remove(excludedUser.getNickname());
    }

    public void lastMessages(User user) {
        ConcurrentLinkedDeque<String> temp = new ConcurrentLinkedDeque<>(messageHandler.getQueue());
        for (int i = 0; i < 10; i++) {
            String poll;
            if ((poll = temp.poll()) != null) {
                printWriterHandler.getMap().get(user.getNickname()).println(poll);
            } else {
                i = 10;
            }
        }
    }

    private void addMessageToMessageHandler(String message, User user) {
        if (this.messageHandler.getQueue().size() > 10) {
            this.messageHandler.getQueue().removeFirst();
            this.messageHandler.getQueue().add(user.getNickname() + ": " + message);
        } else {
            this.messageHandler.getQueue().add(user.getNickname() + ": " + message);
        }
    }
}
