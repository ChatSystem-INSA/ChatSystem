package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.model.Message;
import com.insatoulouse.chatsystem.model.User;
import com.insatoulouse.chatsystem.model.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatGUI {

    private static final Logger l = LogManager.getLogger(ChatGUI.class.getName());
    public static final String COMMAND_HELLO = "connect";
    public static final String COMMAND_EXIT = "exit";
    private Controller controller;
    private DefaultListModel<Message> listMessage = new DefaultListModel<Message>();

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
            l.debug("Bad command " + text);
        }
    }

    private void commandToController(String command, String [] args){
        if(COMMAND_HELLO.equals(command) && args.length == 1){
            controller.processConnection(args[0]);
        }
        else if(COMMAND_EXIT.equals(command) && args.length == 0){
            this.sendExit();
        }
        else{
            l.debug("Bad command "+command);
        }
    }

    public void addMessage(Message m){
        listMessage.addElement(m);
    }

    public void removeMessage(Message m){
        listMessage.removeElement(m);
    }

    public DefaultListModel<Message> getListMessage(){
        return listMessage;
    }

    /**
     * Send exit to controller
     */
    public void sendExit(){
        controller.processExit();
    }
}
