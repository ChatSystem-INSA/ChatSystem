package com.insatoulouse.chatsystem.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class MessageParserJson implements MessageParser {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Message read(InputStream data) throws IOException {
        return mapper.readValue(data,Hello.class);
    }

    @Override
    public void write(OutputStream out, Message data)throws IOException {
        mapper.writeValue(out, data);
    }
}