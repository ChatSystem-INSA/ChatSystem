package com.insatoulouse.chatsystem.model.network;

/**
 * Goodbye class
 * Signal send/receive to/from user when want disconnected
 */
public class Goodbye extends Packet {

    public Goodbye() {
        super(Packet.TYPE_GOODBYE);
    }

}
