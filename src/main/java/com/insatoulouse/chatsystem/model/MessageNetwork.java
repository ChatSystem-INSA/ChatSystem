package com.insatoulouse.chatsystem.model;

/**
 * Created by tlk on 05/12/14.
 */
public class MessageNetwork implements DisplayMessage {

    private User user;
    private String message;

    public MessageNetwork(User u, String message) {
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
}
