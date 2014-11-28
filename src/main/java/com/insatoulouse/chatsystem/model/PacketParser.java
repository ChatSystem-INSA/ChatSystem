package com.insatoulouse.chatsystem.model;

import com.insatoulouse.chatsystem.exception.PacketException;

import java.io.IOException;
import java.io.OutputStream;

public interface PacketParser {
    public Packet read(String data) throws PacketException;
    public void write(OutputStream out, Packet data) throws IOException;
}
