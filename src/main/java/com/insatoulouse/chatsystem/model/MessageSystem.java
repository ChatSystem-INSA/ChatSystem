package com.insatoulouse.chatsystem.model;

/**
 * Created by tlk on 27/11/14.
 */
public class MessageSystem implements DisplayMessage {

    private String data;

    public MessageSystem(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
