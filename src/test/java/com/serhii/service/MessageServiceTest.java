package com.serhii.service;

import com.serhii.handler.MessageHandler;
import com.serhii.handler.PrintWriterHandler;
import com.serhii.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@RunWith(SpringRunner.class)
public class MessageServiceTest {
    @Mock
    private PrintWriterHandler printWriterHandler;
    @Mock
    private MessageHandler messageHandler;
    @InjectMocks
    private MessageService messageService;

    private User excludedUser = new User();

    @Before
    public void init(){
        Mockito.when(printWriterHandler.getMap()).thenReturn(new ConcurrentHashMap<>());
        Mockito.when(messageHandler.getQueue()).thenReturn(new ConcurrentLinkedDeque<>());
        excludedUser.setNickname("excludedUserNickname");
        excludedUser.setPassword("excludedUserPassword");
    }
    @Test
    public void sendTestWithDifferentNickname(){
        printWriterHandler.getMap().put("testNickname", new PrintWriter(System.out));
        messageService.send("testMessage", excludedUser);
    }

    @Test
    public void sendTestWithSameNickname(){
        excludedUser.setNickname("testNickname");
        excludedUser.setPassword("testPassword");

        printWriterHandler.getMap().put("testNickname", new PrintWriter(System.out));
        messageService.send("testMessage", excludedUser);
    }

    @Test
    public void sendTestWithFullQueue(){
        printWriterHandler.getMap().put("testNickname", new PrintWriter(System.out));
        for (int i = 0; i < 11; i++) {
            messageHandler.getQueue().add("TestMessage " + i);
        }
        messageService.send("testMessage", excludedUser);
    }

    @Test
    public void sendUserLoginAndExitTest(){
        printWriterHandler.getMap().put("testNickname", new PrintWriter(System.out));
        messageService.sendUserLogin(excludedUser);
    }

    @Test
    public void lastMessagesTest(){
        for (int i = 0; i < 10; i++) {
            messageHandler.getQueue().add(String.valueOf(i));
        }
        printWriterHandler.getMap().put(excludedUser.getNickname(), new PrintWriter(System.out));
        int queueSize = messageService.lastMessages(excludedUser);

        Assert.assertEquals("Queue is empty",0, queueSize);

        messageService.lastMessages(excludedUser);
    }
}
