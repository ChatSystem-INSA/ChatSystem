package com.insatoulouse.chatsystem.model;

import java.io.IOException;
import java.io.OutputStream;

public interface PacketParser {
    public Packet read(String data) throws MessageException;
    public void write(OutputStream out, Packet data) throws IOException;
}
