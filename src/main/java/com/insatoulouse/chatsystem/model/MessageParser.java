package com.insatoulouse.chatsystem.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface MessageParser {
    public Message read(InputStream data) throws IOException;
    public void write(OutputStream out, Message data) throws IOException;
}