package com.insatoulouse.chatsystem.model;


public class Hello extends Packet {

    public static final String type = "hello";

    private String username;

    public Hello(String data) {
        this.username = data;
    }
}
