package com.serhii;

import com.serhii.engine.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class SpringChatApplication implements CommandLineRunner{
    private static final Logger logger = LoggerFactory.getLogger(SpringChatApplication.class);

    @Autowired
	private ServerSocket serverSocket;

	@Autowired
	private Worker worker;

	public static void main(String[] args) {
		SpringApplication.run(SpringChatApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		logger.info("Server is working!");
		while (true) {
			Socket socket = serverSocket.accept();
			worker.setSocket(socket);
			new Thread(worker).start();
		}
	}
}
