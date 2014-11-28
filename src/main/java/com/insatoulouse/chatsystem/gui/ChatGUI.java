package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatGUI {

    private static final Logger l = LogManager.getLogger(ChatGUI.class.getName());
    public static final String COMMAND_HELLO = "connect";
    private Controller controller;

    public ChatGUI(Controller c) {
        this.controller = c;
        new ChatFrame(this);
    }

    public void executeCommand(String text) {
        l.debug("Command " + text);
        String[] s = text.split(" ");
        if(s.length > 0){
            commandToController(s[0], Arrays.copyOfRange(s, 1, s.length));
        }
        else{
            l.debug("Bad command "+text);
        }
    }

    private void commandToController(String command, String [] args){
        if(COMMAND_HELLO.equals(command) && args.length == 1){
            controller.processConnection(args[0]);
        }
        else{
            l.debug("Bad command "+command);
        }
    }
}
