/*
 * Chat System - P2P
 *     Copyright (C) 2014 LIVET BOUTOILLE
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
import java.util.List;

/**
 * ChatGUI class
 * It's a facade of Graphic User Interface
 * Class communicate with a Controller
 */
public class ChatGUI implements WindowListener {

    private final static String TITLE = "Super ChatSystem";
    private static final Logger l = LogManager.getLogger(ChatGUI.class.getName());
    /**
     * Controller
     */
    private final Controller controller;
    /**
     * General JFrame
     */
    private final JFrame frame;
    /**
     * Chat view
     */
    private Chat chat;
    /**
     * Login view
     */
    private Login dialog;

    public ChatGUI(Controller controller) {
        this.controller = controller;
        controller.setChatGUI(this);

        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(this);

        showLogin();

        frame.setVisible(true);
    }

    private void showLogin() {
        try {
            l.trace("Show dialog login");
            dialog = new Login(this, controller.getNetworkBroadcastAddresses());
            frame.setContentPane(dialog.getPanel());
            frame.setSize(400, 150);
        } catch (TechnicalException e) {
            ExceptionManager.manage(e);
        }
    }

    /**
     * Send message to network
     *
     * @param currentChatuser receiver user
     * @param text            message to send
     */
    public void sendMessage(RemoteUser currentChatuser, String text) {
        l.trace("Send message to " + currentChatuser.getName() + " : " + text);
        if (text.contains("/flood")) {
            for (int i = 0; i < 500; i++) {
                controller.processSendMessage(currentChatuser, text.replace("/flood", ""));
            }
        } else {
            controller.processSendMessage(currentChatuser, text);
        }
    }

    /**
     * Send hello to one network (chose with broadcast)
     *
     * @param text username
     * @param addr broadcast address
     */
    public void sendUsername(String text, InetAddress addr) {
        l.trace("Send username connection : " + text);
        controller.processConnection(text, addr);
    }

    /**
     * Disconnection of chat, send goodbye
     */
    public void sendLogout() {
        l.trace("Send logout");

        // Close mainframe
        controller.processDisconnect();


        showLogin();
    }

    /**
     * New message from network
     *
     * @param messageNetwork network message
     */
    public void newMessage(MessageNetwork messageNetwork) {
        l.trace("New message to GUI " + messageNetwork.toString());
        if (chat != null) {
            chat.newMessage(messageNetwork);
        } else {
            l.error("Invalid state, chat panel is null.");
        }
    }

    /**
     * Start chat view
     *
     * @param u Local User
     */
    public void startChat(LocalUser u) {
        l.trace("Start chat panel");
        chat = new Chat(this, u);
        frame.setContentPane(this.chat.getPanel());
        frame.setSize(700, 400);
    }

    /**
     * Add user when receiving hello from network
     *
     * @param u new user
     */
    public void addUser(RemoteUser u) {
        l.trace("Add user " + u.toString());
        if (chat != null) {
            chat.addUser(u);
        } else {
            l.error("Invalid state, chat panel is null.");
        }
    }

    /**
     * Remove user when receiving goodbye from network
     *
     * @param u user to delete
     */
    public void removeUser(RemoteUser u) {
        l.trace("Remove user " + u);
        if (chat != null) {
            chat.removeUser(u);
        } else {
            l.error("Invalid state, chat panel is null.");
        }
    }

    /**
     * Send file to network
     *
     * @param u    user to send file
     * @param file file
     */
    public void sendFile(RemoteUser u, File file) {
        l.trace("Send file " + file);
        controller.processSendfile(u, file);
    }

    public List<RemoteUser> getRemoteUser() {
        return controller.getUsers();
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
