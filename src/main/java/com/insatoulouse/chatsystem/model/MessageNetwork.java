package com.insatoulouse.chatsystem.model;

/**
 * Created by tlk on 05/12/14.
 */
public class MessageNetwork implements DisplayMessage {


    public static final int IN = 1;
    public static final int OUT = 2;

    private int type;
    private User user;
    private String message;

    public MessageNetwork(User u, String message)
    {
        this.type = MessageNetwork.IN;
        this.user = u;
        this.message = message;
    }

    public MessageNetwork(int type, User u, String message) {
        this.type = type;
        this.user = u;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
