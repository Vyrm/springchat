package com.serhii.handler;

import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PrintWriterHandler {
    private ConcurrentHashMap<String, PrintWriter> printWriters;

    public PrintWriterHandler() {
        printWriters = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, PrintWriter> getMap() {
        return printWriters;
    }
}
