package com.insatoulouse.chatsystem.model.network;

/**
 * Goodbye class
 * Signal send/receive to/from user when want disconnected
 * {
 *     "type":"goodBye"
 * }
 */
public class Goodbye implements Packet {

    public Goodbye() {
        super();
    }

}
