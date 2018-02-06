package com.serhii.handler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
public class PrintWriterHandlerTest {
    @Mock
    private ConcurrentHashMap<String, PrintWriter> concurrentHashMap;

    @InjectMocks
    private PrintWriterHandler printWriterHandler;

    @Test
    public void getMapTest(){
        Assert.assertTrue(printWriterHandler.getMap().equals(concurrentHashMap));
    }
}
