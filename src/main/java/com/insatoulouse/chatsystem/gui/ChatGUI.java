package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.model.DisplayMessage;
import com.insatoulouse.chatsystem.model.MessageSystem;
import com.insatoulouse.chatsystem.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.Arrays;

public class ChatGUI {

    private static final Logger l = LogManager.getLogger(ChatGUI.class.getName());

    public static final String COMMAND_CONNECT = "connect";
    public static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_QUIT = "quit";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_HELP = "help";

    private final ChatFrame chatFrame;
    private Controller controller;
    private DefaultListModel<DisplayMessage> listMessage = new DefaultListModel<DisplayMessage>();

    public ChatGUI(Controller c) {
        this.controller = c;
        chatFrame = new ChatFrame(this);
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
        if(COMMAND_CONNECT.equals(command) && args.length == 1){
            controller.processConnection(args[0]);
        }
        else if(COMMAND_EXIT.equals(command) && args.length == 0){
            this.sendExit();
        }
        else if(COMMAND_QUIT.equals(command) && args.length == 0){
            controller.processDisconnect();
        }
        else if(COMMAND_LIST.equals(command) && args.length == 0) {
            addMessage(new MessageSystem("List of users :"));
            for(User u : this.controller.getUsers())
            {
                addMessage(new MessageSystem("    - "+u.getName() + "@" + u.getIp().toString()));
            }
        } else if(COMMAND_HELP.equals(command) && args.length == 0)
        {
            addMessage(new MessageSystem("List of commands :"));
            addMessage(new MessageSystem("   help - print this help"));
            addMessage(new MessageSystem("   connect <username> - connect to the chat with <username> as name"));
            addMessage(new MessageSystem("   quit - disconnect of chat"));
            addMessage(new MessageSystem("   list - print list of connected users"));
            addMessage(new MessageSystem("   exit - exit the program"));
        }
        else{
            addMessage(new MessageSystem(MessageSystem.ERROR, "Command "+command + " is invalid. Try again or try help."));
            l.debug("Bad command " + command);
        }
    }

    public void addMessage(DisplayMessage m){
        listMessage.addElement(m);
    }

    public void removeMessage(DisplayMessage m){
        listMessage.removeElement(m);
    }

    public DefaultListModel<DisplayMessage> getListMessage(){
        return listMessage;
    }

    /**
     * Send exit to controller
     */
    public void sendExit(){
        controller.processExit();
    }

    public void setLocalUser(User u){
        chatFrame.onLocalUserChange(u);
    }
}
