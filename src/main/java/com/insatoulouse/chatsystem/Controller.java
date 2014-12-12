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

package com.insatoulouse.chatsystem;

import com.insatoulouse.chatsystem.exception.ExceptionManager;
import com.insatoulouse.chatsystem.exception.LogicalException;
import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.model.LocalUser;
import com.insatoulouse.chatsystem.model.MessageNetwork;
import com.insatoulouse.chatsystem.model.RemoteUser;
import com.insatoulouse.chatsystem.model.User;
import com.insatoulouse.chatsystem.model.network.Message;
import com.insatoulouse.chatsystem.model.network.MessageAck;
import com.insatoulouse.chatsystem.ni.ChatNI;
import com.insatoulouse.chatsystem.utils.Sound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Controller class
 */
public class Controller {

    private static final Logger l = LogManager.getLogger(Controller.class.getName());

    private ChatGUI chatGUI;
    private ChatNI chatNI;

    private ArrayList<RemoteUser> users = new ArrayList<RemoteUser>();
    private LocalUser localUser;

    /**
     * Process connection
     * From GUI
     * @param username local user to connect
     * @param bdr Broadcast address of network
     */
    public void processConnection(String username, InetAddress bdr)
    {
        if(!isConnected())
        {
            try {
                LocalUser user = new LocalUser(username);
                chatNI.start(bdr);
                chatNI.sendHello(user);
                localUser = user;
                chatGUI.startChat(user,getUsers());
            } catch (TechnicalException e) {
                ExceptionManager.manage(e);
            } catch (LogicalException e) {
                ExceptionManager.manage(e);
            } catch (UnknownHostException e) {
                ExceptionManager.manage(new TechnicalException(e));
            }

        } else {
            l.warn("Invalid state : already connected");
        }

    }

    /**
     * Process disconnect
     * From GUI
     */
    public void processDisconnect(){
        if(isConnected())
        {
            try {
                chatNI.sendGoodbye();
                users.clear();
                localUser = null;
                if(chatNI != null)
                    chatNI.exit();
            } catch (TechnicalException e) {
                ExceptionManager.manage(e);
            }
        }
        else{
            l.warn("Invalid state : not already connected");
        }
    }

    /**
     * Process send message
     * From GUI
     * @param u user to send
     * @param mess data to send
     */
    public void processSendMessage(RemoteUser u, String mess) {
        assert u != null;
        if(isConnected())
        {
            try {
                chatNI.sendMessage(u, mess);
                chatGUI.newMessage(new MessageNetwork(MessageNetwork.OUT, u, mess));
            } catch (TechnicalException e) {
                ExceptionManager.manage(e);
            } catch (LogicalException e) {
                ExceptionManager.manage(e);
            }

        } else {
            l.warn("Invalid state : not connected.");
        }
    }

    /**
     * Process send file
     * From local user
     * @param to remote user to send the file
     * @param file file to send
     */
    public void processSendfile(RemoteUser to, File file) {
        l.trace("processSenfile");
        this.chatNI.sendFile(to, file);
    }


    /**
     * Process Hello
     * Send a helloAck with localUser username
     * From Network
     * @param u new RemoteUser
     */
    public void processHello(RemoteUser u)
    {
        if(isConnected())
        {
            if(!userExists(u))
            {
                try {
                    this.chatNI.sendHelloAck(getLocalUser(), u);
                    addUser(u);
                } catch (TechnicalException e) {
                    ExceptionManager.manage(e);
                } catch (LogicalException ignored) {} // Ignored because error isn't came from localuser
            } else {
                l.error("Un utilisateur existe déjà  : " + u);
            }
        } else {
            l.warn("Invalid state : do nothing not connected");
        }
    }

    /**
     * Process HelloAck
     * Add new user connected
     * From Network
     * @param n remote helloAck user
     */
    public void processHelloAck(RemoteUser n)
    {
        if(isConnected())
        {
            if(!userExists(n))
            {
                this.addUser(n);
            } else {
                l.error("Un utilisateur existe déjà : " + n);
            }
        } else {
            l.warn("Invalid state : do nothing, not connected");
        }
    }

    /**
     * Process GoodBye
     * Delete user in list
     * From Network
     * @param addr ip
     */
    public void processGoodBye(InetAddress addr){
        if(isConnected())
        {
            RemoteUser u = getUserByAddr(addr);
            if(u != null)
                this.removeUser(u);
        }
        else{
            l.debug("Invalid state : do nothing, not connected");
        }
    }

    /**
     * Process Message
     * From Network
     * @param message
     * @param addr
     */
    public void processMessage(Message message, InetAddress addr) {
        if(isConnected())
        {
            RemoteUser u = getUserByAddr(addr);
            if(u != null)
            {
                Sound.playSound(Sound.URL_SOUND_MSG);
                chatGUI.newMessage(new MessageNetwork(u, message.getMessageData()));
                try {
                    this.chatNI.sendMessageAck(u, message.getMessageNumber());
                } catch (TechnicalException e) {
                    ExceptionManager.manage(e);
                } catch (LogicalException e) {
                    ExceptionManager.manage(e);
                }
            }
        } else {
            l.debug("Invalid state : not connected, do nothing");
        }
    }

    /**
     * Process MessageAck
     * Not implemented
     * @param mess MessageAck
     * @param addr ip
     */
    public void processMessageAck(MessageAck mess, InetAddress addr)
    {
        l.debug("Message ack not implemented");
    }


    public void processFile(File f, InetAddress addr)
    {
        l.trace("process file!");
        // TODO : display in GUI
    }

    /**
     * Get Network broadcast address
     * @return Network broadcast address
     * @throws TechnicalException
     */
    public ArrayList<InetAddress> getNetworkBroadcastAddresses() throws TechnicalException
    {
        return this.chatNI.getNetworkBroadcastAddresses();
    }

    public synchronized ArrayList<RemoteUser> getUsers() {
        return users;
    }

    /**
     * Process exit
     * From GUI
     */
    public void processExit() {
        l.trace("Process exit");
        processDisconnect();
        System.exit(0);
    }

    public boolean isConnected()
    {
        return this.getLocalUser() != null;
    }

    public void setChatNI(ChatNI chatNI) {
        this.chatNI = chatNI;
    }

    public void setChatGUI(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
    }

    private synchronized void addUser(RemoteUser u)
    {
        l.debug("New user : " + u.toString());
        users.add(u);
        chatGUI.addUser(u);
    }

    private synchronized void removeUser(RemoteUser u)
    {
        l.debug("Remove user : " + u.toString());
        users.remove(u);
        chatGUI.removeUser(u);
    }

    private boolean userExists(User u)
    {
        if(getUserByAddr(u.getIp()) != null)
        {
            return true;
        }

        if(getUserByUsername(u.getName()) != null)
        {
            return true;
        }

        if(getLocalUser().getIp().equals(u.getIp()))
        {
            return true;
        }

        if(getLocalUser().getName().equals(u.getName()))
        {
            return true;
        }
        return false;
    }

    private synchronized RemoteUser getUserByAddr(InetAddress addr)
    {
        RemoteUser ret = null;
        for(RemoteUser u : users)
        {
            if(u.getIp().equals(addr))
            {
                ret = u;
                break;
            }
        }
        return ret;
    }

    private synchronized RemoteUser getUserByUsername(String username)
    {
        RemoteUser ret = null;
        for(RemoteUser u: users)
        {
            if(u.getName().equals(username))
            {
                ret = u;
                break;
            }
        }
        return ret;
    }

    private synchronized User getLocalUser()
    {
        return localUser;
    }

}
