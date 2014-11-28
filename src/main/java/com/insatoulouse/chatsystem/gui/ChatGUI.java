package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatGUI {

    private static final Logger l = LogManager.getLogger(ChatGUI.class.getName());
    private Controller controller;

    public ChatGUI(Controller c) {
        this.controller = c;
        new ChatFrame(this);
    }

    public void executeCommand(String text) {
        l.debug("Command " + text);
    }
}
