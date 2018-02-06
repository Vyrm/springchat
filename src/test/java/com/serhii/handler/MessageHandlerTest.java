package com.serhii.handler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentLinkedDeque;

@RunWith(SpringRunner.class)
public class MessageHandlerTest {
    @Mock
    private ConcurrentLinkedDeque<String> concurrentLinkedDeque;

    @InjectMocks
    private MessageHandler messageHandler;

    @Test
    public void getQueueTest(){
        Assert.assertTrue(messageHandler.getQueue().equals(concurrentLinkedDeque));
    }
}
