package com.insatoulouse.chatsystem.gui;

public class ChatGUI {

    public ChatGUI() {
        new ChatFrame(this);
    }

    public void executeCommand(String text) {
        System.out.println("Command "+ text);
    }
}
