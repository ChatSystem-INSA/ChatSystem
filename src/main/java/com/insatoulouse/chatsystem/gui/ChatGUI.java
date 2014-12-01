package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.model.MessageSystem;
import com.insatoulouse.chatsystem.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
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
    private DefaultListModel<MessageSystem> listMessage = new DefaultListModel<MessageSystem>();
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
            addMessage(new MessageSystem("List of commands :"));
            addMessage(new MessageSystem("help - print this help"));
            addMessage(new MessageSystem("exit - exit the program"));
            addMessage(new MessageSystem("quit - disconnect of chat"));
            addMessage(new MessageSystem("list - print list of connected users"));
            this.chatFrame.switchChatPanel();
        }
        else{
            addMessage(new MessageSystem("Command "+command + " is invalid. Try again or try help."));
            l.debug("Bad command " + command);
        }
    }

    public void addUser(User u)
    {
        listUser.addElement(u);
        this.addMessage(new MessageSystem("New user : " + u.getName()));
    }
    public void removeUser(User u)
    {
        listUser.removeElement(u);
    }

    public void removeAllUser()
    {
        listUser.removeAllElements();
    }

    public void addMessage(MessageSystem m){
        listMessage.addElement(m);
    }

    public void removeMessage(MessageSystem m){
        listMessage.removeElement(m);
    }

    public DefaultListModel<MessageSystem> getListMessage(){
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
