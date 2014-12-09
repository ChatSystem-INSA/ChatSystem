package com.insatoulouse.chatsystem.model.network;

/**
 * HelloAck class
 * Signal is sent to new user
 * Signal is received from other user when send Hello
 * @see Hello
 */
public class HelloAck extends Packet {

    private String userName;

    public HelloAck(String userName) {
        super(Packet.TYPE_HELLO_ACK);
        this.userName = userName;
    }

    public HelloAck(){
        this("");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
