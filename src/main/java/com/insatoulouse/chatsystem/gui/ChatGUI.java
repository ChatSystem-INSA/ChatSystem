package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.Arrays;

public class ChatGUI {

    private Controller controller;
    private Chat chat;
    private Login dialog = new Login(this);
    private JFrame frame;

    public ChatGUI(Controller controller) {
        this.controller = controller;
        controller.setChatGUI(this);
        dialog.pack();
        dialog.setVisible(true);
    }


    public void sendMessage(RemoteUser currentChatuser, String text) {
        controller.processSendMessage(currentChatuser,text);
    }

    public void sendUsername(String text) {
        controller.processConnection(text);
    }

    public void sendLogout() {
        controller.processDisconnect();
        frame.dispose();

        dialog = new Login(this);
        dialog.pack();
        dialog.setVisible(true);
    }

    public void newMessage(MessageNetwork messageNetwork) {
        if(chat != null){
            chat.newMessage(messageNetwork);
        }
    }

    public void setLocalUser(User local) {
        chat = new Chat(this,local);
        frame = new JFrame("Chat");
        frame.setContentPane(this.chat.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public void addUser(RemoteUser u) {
        if(chat != null){
            chat.addUser(u);
        }
    }

    public void removeUser(RemoteUser u) {
        if(chat != null){
            chat.removeUser(u);
        }
    }
}
