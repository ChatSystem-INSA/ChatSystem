package com.insatoulouse.chatsystem.model;

import java.io.IOException;
import java.io.OutputStream;

public interface MessageParser {
    public Message read(String data) throws MessageException;
    public void write(OutputStream out, Message data) throws IOException;
}
