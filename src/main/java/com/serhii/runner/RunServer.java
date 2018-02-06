package com.serhii.runner;

import com.serhii.SpringChatApplication;
import com.serhii.engine.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class RunServer {
    private static final Logger logger = LoggerFactory.getLogger(SpringChatApplication.class);
    @Autowired
    private ServerSocket serverSocket;

    @Autowired
    private Worker worker;

    @PostConstruct
    public void run() throws IOException {
        logger.info("Server is working!");
        while (true) {
            Socket socket = serverSocket.accept();
            worker.setSocket(socket);
            new Thread(worker).start();
        }
    }
}
