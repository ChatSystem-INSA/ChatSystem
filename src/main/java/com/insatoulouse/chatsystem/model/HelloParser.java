package com.insatoulouse.chatsystem.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface HelloParser {
    public Hello read(InputStream data) throws IOException;
    public void write(OutputStream out, Hello data) throws IOException;
}