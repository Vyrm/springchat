package com.serhii.service;

import com.serhii.exception.UserDisconnectedException;
import com.serhii.handler.PrintWriterHandler;
import com.serhii.handler.UserHandler;
import com.serhii.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
public class AuthorizationServiceTest {
    @Mock
    private UserHandler userHandler;
    @Mock
    private PrintWriterHandler printWriterHandler;
    @Mock
    private BufferedReader bufferedReader;
    @Mock
    private PrintWriter printWriter;

    private User user = new User();

    @InjectMocks
    private AuthorizationService authorizationService;

    @Before
    public void init() {
        user.setNickname("testNickname");
        user.setPassword("testPassword");
    }

    @Test(expected = UserDisconnectedException.class)
    public void exitAndNullNickname() throws IOException, UserDisconnectedException {
        Mockito.when(bufferedReader.readLine()).thenReturn(null);
        authorizationService.authorize(bufferedReader, printWriter);
    }

    @Test(expected = UserDisconnectedException.class)
    public void AuthorizeRegistrationFailed() throws IOException, UserDisconnectedException {
        Mockito.when(bufferedReader.readLine()).thenReturn("testNickname", "testPassword", "testPasswordFake", null);
        User testUser = authorizationService.authorize(bufferedReader, printWriter);
        Assert.assertTrue(user.equals(testUser));
    }

    @Test(expected = UserDisconnectedException.class)
    public void AuthorizeLoginFailed() throws IOException, UserDisconnectedException {
        Mockito.when(bufferedReader.readLine()).thenReturn("testNickname", "testPasswordFake", null);
        User testUser = authorizationService.authorize(bufferedReader, printWriter);
        Assert.assertTrue(user.equals(testUser));
    }

    @Test
    public void AuthorizeRegistrationSuccess() throws IOException, UserDisconnectedException {
        Mockito.when(bufferedReader.readLine()).thenReturn("testNickname", "testPassword", "testPassword");
        Mockito.when(printWriterHandler.getMap()).thenReturn(new ConcurrentHashMap<>());
        User testUser = authorizationService.authorize(bufferedReader, printWriter);
        Assert.assertTrue(user.equals(testUser));
    }

    @Test
    public void AuthorizeLoginSuccess() throws IOException, UserDisconnectedException {
        Mockito.when(bufferedReader.readLine()).thenReturn("testNickname", "testPassword");
        Mockito.when(printWriterHandler.getMap()).thenReturn(new ConcurrentHashMap<>());
        Mockito.when(userHandler.getUser("testNickname")).thenReturn(user);
        User testUser = authorizationService.authorize(bufferedReader, printWriter);
        Assert.assertTrue(user.equals(testUser));
    }
}
