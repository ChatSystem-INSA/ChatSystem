package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.model.Message;
import com.insatoulouse.chatsystem.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatGUI {

    private static final Logger l = LogManager.getLogger(ChatGUI.class.getName());

    public static final String COMMAND_HELLO = "connect";
    public static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_QUIT = "quit";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_HELP = "help";

    private final ChatFrame chatFrame;
    private Controller controller;
    private DefaultListModel<Message> listMessage = new DefaultListModel<Message>();
    private DefaultListModel<User> listUser = new DefaultListModel<User>();

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
        if(COMMAND_HELLO.equals(command) && args.length == 1){
            this.chatFrame.switchChatPanel();
            controller.processConnection(args[0]);
        }
        else if(COMMAND_EXIT.equals(command) && args.length == 0){
            this.sendExit();
        }
        else if(COMMAND_QUIT.equals(command) && args.length == 0){
            this.chatFrame.switchChatPanel();
            controller.processDisconnect();
        }
        else if(COMMAND_LIST.equals(command) && args.length == 0) {
            this.chatFrame.switchUserlistPanel();
        } else if(COMMAND_HELP.equals(command) && args.length == 0)
        {
            addMessage(new Message("List of commands :"));
            addMessage(new Message("help - print this help"));
            addMessage(new Message("exit - exit the program"));
            addMessage(new Message("quit - disconnect of chat"));
            addMessage(new Message("list - print list of connected users"));
            this.chatFrame.switchChatPanel();
        }
        else{
            addMessage(new Message("Command "+command + " is invalid. Try again or try help."));
            l.debug("Bad command " + command);
        }
    }

    public void addUser(User u)
    {
        listUser.addElement(u);
        this.addMessage(new Message("New user : " + u.getName()));
    }
    public void removeUser(User u)
    {
        listUser.removeElement(u);
    }

    public void removeAllUser()
    {
        listUser.removeAllElements();
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

    public DefaultListModel<User> getListUser() {
        return listUser;
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
