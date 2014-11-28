package com.insatoulouse.chatsystem.model;


public class Hello extends Packet {

    private String userName;

    public Hello(String data) {
        super(Packet.TYPE_HELLO);
        this.userName = data;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
