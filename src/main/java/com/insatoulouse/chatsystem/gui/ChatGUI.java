package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatGUI implements WindowListener{

    private static final Logger l = LogManager.getLogger(ChatGUI.class.getName());

    public final static String TITLE = "Chat";
    private Controller controller;
    private Chat chat;
    private Login dialog;
    private JFrame frame;

    public ChatGUI(Controller controller) {
        this.controller = controller;
        controller.setChatGUI(this);

        // Show dialog connection
        dialog = new Login(this);
        dialog.pack();
        dialog.setTitle(TITLE);
        dialog.setVisible(true);
    }


    public void sendMessage(RemoteUser currentChatuser, String text) {
        l.trace("Send message to "+currentChatuser.getName()+" : "+text);
        controller.processSendMessage(currentChatuser,text);
    }

    public void sendUsername(String text) {
        l.trace("Send username connection : "+text);
        controller.processConnection(text);
    }

    public void sendLogout() {
        l.trace("Send logout");
        // Close mainframe
        controller.processDisconnect();
        frame.dispose();

        // Show dialog connection
        dialog = new Login(this);
        dialog.pack();
        dialog.setTitle(TITLE);
        dialog.setVisible(true);
    }

    public void newMessage(MessageNetwork messageNetwork) {
        if(chat != null){
            chat.newMessage(messageNetwork);
        }
        else{
            l.error("Invalid state, chat panel is null.");
        }
    }

    public void startChat(ArrayList<User> users) {
        chat = new Chat(this,users);
        frame = new JFrame("Chat");
        frame.setContentPane(this.chat.getPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(this);
        frame.pack();
        frame.setVisible(true);
    }

    public void addUser(RemoteUser u) {
        if(chat != null){
            chat.addUser(u);
        }
        else{
            l.error("Invalid state, chat panel is null.");
        }
    }

    public void removeUser(RemoteUser u) {
        if(chat != null){
            chat.removeUser(u);
            l.trace("Remove user "+ u.getName() );
        }
        else{
            l.error("Invalid state, chat panel is null.");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        controller.processExit();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
