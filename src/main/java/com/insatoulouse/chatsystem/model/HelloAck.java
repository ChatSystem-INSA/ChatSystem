package com.insatoulouse.chatsystem.model;

/**
 * Created by Banana on 28/11/14.
 */
public class HelloAck extends Packet {

    private String userName;

    public HelloAck(String userName) {
        super(Packet.TYPE_HELLO_ACK);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
