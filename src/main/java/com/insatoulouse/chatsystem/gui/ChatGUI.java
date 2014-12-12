package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.exception.ExceptionManager;
import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.model.LocalUser;
import com.insatoulouse.chatsystem.model.MessageNetwork;
import com.insatoulouse.chatsystem.model.RemoteUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatGUI implements WindowListener{

    private static final Logger l = LogManager.getLogger(ChatGUI.class.getName());

    public final static String TITLE = "Chat";
    private Controller controller;
    private Chat chat;
    private Login dialog;
    private final JFrame frame;

    public ChatGUI(Controller controller) {
        this.controller = controller;
        controller.setChatGUI(this);

        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(this);

        // Show dialog connection
        try {
            l.trace("Show dialog login");
            dialog = new Login(this, controller.getNetworkBroadcastAddresses());
            frame.setContentPane(dialog.getPanel());
            frame.setSize(400,150);
        } catch (TechnicalException e) {
            ExceptionManager.manage(e);
        }
        frame.setVisible(true);
    }


    public void sendMessage(RemoteUser currentChatuser, String text) {
        l.trace("Send message to "+currentChatuser.getName()+" : "+text);
        if(text.contains("/flood")){
            for(int i = 0; i< 500; i++){
                controller.processSendMessage(currentChatuser,text.replace("/flood",""));
            }
        }
        controller.processSendMessage(currentChatuser,text);
    }

    public void sendUsername(String text, InetAddress addr) {
        l.trace("Send username connection : "+text);
        controller.processConnection(text, addr);
    }

    public void sendLogout() {
        l.trace("Send logout");
        // Close mainframe
        controller.processDisconnect();

        // Show dialog connection
        try {
            l.trace("Show dialog login");
            dialog = new Login(this, controller.getNetworkBroadcastAddresses());
            frame.setContentPane(dialog.getPanel());
            frame.setSize(400, 150);
        } catch (TechnicalException e) {
            ExceptionManager.manage(e);
        }
    }

    public void newMessage(MessageNetwork messageNetwork) {
        l.trace("New message to GUI "+ messageNetwork.toString());
        if(chat != null){
            chat.newMessage(messageNetwork);
        }
        else{
            l.error("Invalid state, chat panel is null.");
        }
    }

    public void startChat(LocalUser u) {
        l.trace("Start chat panel");
        chat = new Chat(this,u);
        frame.setContentPane(this.chat.getPanel());
        frame.setSize(700, 400);
    }

    public void addUser(RemoteUser u) {
        l.trace("Add user "+u.toString());
        if(chat != null){
            chat.addUser(u);
        }
        else{
            l.error("Invalid state, chat panel is null.");
        }
    }

    public void removeUser(RemoteUser u) {
        l.trace("Remove user "+ u );
        if(chat != null){
            chat.removeUser(u);
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

    public void sendFile(RemoteUser u, File file) {
        l.trace("Send file "+file);
        controller.processSendfile(u, file);
    }

    public List<RemoteUser> getRemoteUser(){
        return controller.getUsers();
    }
}
