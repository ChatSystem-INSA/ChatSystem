package com.insatoulouse.chatsystem.model.network.dao;

import com.insatoulouse.chatsystem.exception.PacketException;
import com.insatoulouse.chatsystem.model.network.Packet;

public interface PacketParser {
    public Packet read(String data) throws PacketException;
    public String write(Packet data) throws PacketException;
}
