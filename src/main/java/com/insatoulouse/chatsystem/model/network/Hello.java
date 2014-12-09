package com.insatoulouse.chatsystem.model.network;

/**
 * Hello class
 * Signal send/receive to/from network when a User connect to ChatSystem
 */
public class Hello extends Packet {

    private String userName;

    public Hello(String data) {
        super(Packet.TYPE_HELLO);
        this.userName = data;
    }

    public Hello(){
        this("");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
