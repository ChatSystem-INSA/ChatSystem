package com.insatoulouse.chatsystem.model;

/**
 * Created by tlk on 27/11/14.
 */
public class MessageSystem extends Packet {

    private String data;

    public MessageSystem(String data) {
        super(Packet.TYPE_MESSAGE);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
