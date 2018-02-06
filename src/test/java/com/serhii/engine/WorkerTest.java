package com.serhii.engine;

import com.serhii.exception.UserDisconnectedException;
import com.serhii.model.User;
import com.serhii.service.AuthorizationService;
import com.serhii.service.MessageService;
import com.serhii.util.MockSocket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
public class WorkerTest {
    @Mock
    private MessageService messageService;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private Socket socket;
    @InjectMocks
    private Worker worker;
    private MockSocket mockSocket = new MockSocket();

    @Test
    public void runTest() throws IOException, UserDisconnectedException {
        User testUser = new User();
        testUser.setNickname("testUser");
        testUser.setPassword("testUser");
        Mockito.when(socket.getOutputStream()).thenReturn(mockSocket.getOutputStream());
        Mockito.when(socket.getInputStream()).thenReturn(mockSocket.getInputStream());
        Mockito.when(authorizationService.authorize(any( BufferedReader.class), any(PrintWriter.class))).thenReturn(testUser);
        worker.run();
    }

}
