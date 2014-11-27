package com.insatoulouse.chatsystem.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatGUI {

    private static final Logger l = LogManager.getLogger(ChatGUI.class.getName());

    public ChatGUI() {
        new ChatFrame(this);
    }

    public void executeCommand(String text) {
        l.debug("Command "+ text);
    }
}
