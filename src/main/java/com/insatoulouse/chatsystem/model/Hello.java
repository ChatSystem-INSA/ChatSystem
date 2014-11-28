package com.insatoulouse.chatsystem.model;


public class Hello extends Message {

    public static final String type = "hello";

    private String username;

    public Hello(String name)
    {
        this.username = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
