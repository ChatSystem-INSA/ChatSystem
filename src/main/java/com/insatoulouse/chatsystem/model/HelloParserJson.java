package com.insatoulouse.chatsystem.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class HelloParserJson implements HelloParser {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Hello read(InputStream data) throws IOException {
        return mapper.readValue(data,Hello.class);
    }

    @Override
    public void write(OutputStream out, Hello data)throws IOException {
        mapper.writeValue(out, data);
    }
}