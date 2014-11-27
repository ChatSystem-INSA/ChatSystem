package com.insatoulouse.chatsystem.model;

/**
 * Created by tlk on 27/11/14.
 */
public class Message {

    private String type;

    private String data;

    public Message(String data) {
        this.data = data;
    }

    public Message() {
        this.data = "";
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

   
}
