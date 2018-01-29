package com.serhii.handler;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class MessageHandler {
    private ConcurrentLinkedDeque<String> concurrentLinkedDeque;

    public MessageHandler() {
        concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    }

    public ConcurrentLinkedDeque<String> getQueue() {
        return concurrentLinkedDeque;
    }
}
