package com.serhii.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MockSocket extends Socket{
    public MockSocket() {

    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream("String from client".getBytes());
    }

    public OutputStream getOutputStream() {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
    }
}
