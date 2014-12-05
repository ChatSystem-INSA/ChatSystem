package com.insatoulouse.chatsystem.model;

/**
 * Created by tlk on 27/11/14.
 */
public class MessageSystem implements DisplayMessage {

    public static final int INFO = 1;
    public static final int ERROR = 2;

    private String data;
    private int type;

    public MessageSystem(String data) {
        this.data = data;
        this.type = MessageSystem.INFO;
    }

    public MessageSystem(int type, String data)
    {
        this.type = type;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

}
