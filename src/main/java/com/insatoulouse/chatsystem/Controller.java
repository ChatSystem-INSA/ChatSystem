package com.insatoulouse.chatsystem;


import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.gui.Chat;
import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.model.*;
import com.insatoulouse.chatsystem.ni.ChatNI;
import com.insatoulouse.chatsystem.ni.NetworkInvoker;
import com.insatoulouse.chatsystem.utils.Sound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Controller {

    private static final Logger l = LogManager.getLogger(Controller.class.getName());

    private ChatGUI chatGUI;
    private ChatNI chatNI;

    private ArrayList<User> users = new ArrayList<User>();

    public Controller()
    {
    }

    /*
    * From GUI
    * */
    public void processConnection(String username, InetAddress addr)
    {
        if(!isConnected())
        {
            try {
                this.chatNI.start(addr);
                User local = new User(true, username, InetAddress.getLocalHost());
                this.chatNI.sendHello(local);
                synchronized(users){
                    this.users.add(local);
                }
                chatGUI.startChat(getUsers());
            } catch (Exception e) {
                l.error("unable to connect", e);
                //chatGUI.addMessage(new MessageSystem(MessageSystem.ERROR, "Unable to connect ..."));
            }
        } else {
            l.debug("already connected");
            //this.chatGUI.addMessage(new MessageSystem(MessageSystem.ERROR, "You're already connected"));
        }

    }

    public void processDisconnect(){
        if(isConnected())
        {
            try {
                this.chatNI.sendGoodbye();
                this.flushUsers();
            } catch (TechnicalException e) {
                l.error("Impossible de lancer le goodbye", e);
            }
        }
        else{
            l.debug("not already connected");
            //this.chatGUI.addMessage(new MessageSystem(MessageSystem.ERROR, "You're not connected"));
        }
    }

    public void processSendMessage(RemoteUser u, String mess) {
        if(isConnected())
        {
            if(u != null )
            {
                try {
                    this.chatNI.sendMessage(u, mess);
                    this.chatGUI.newMessage(new MessageNetwork(MessageNetwork.OUT, u, mess));
                } catch (TechnicalException e) {
                    //this.chatGUI.addMessage(new MessageSystem(MessageSystem.ERROR, "Unable to send message, please retry"));
                }
            } else {
                //this.chatGUI.addMessage(new MessageSystem(MessageSystem.ERROR, "User not existing - try 'list'"));
            }
        } else {
            l.debug("not connected.");
            //this.chatGUI.addMessage(new MessageSystem(MessageSystem.ERROR, "You're not connected"));
        }
    }

    /*
    * From ChatNI*/
    public void processHello(Hello messHello, InetAddress addr)
    {
        if(isConnected())
        {
            RemoteUser u = new RemoteUser(false, messHello.getUserName(), addr);
            if(!userExists(u))
            {
                this.addUser(u);
                try {
                    this.chatNI.sendHelloAck(getLocalUser(), u);
                } catch (TechnicalException e) {
                    l.error("Impossible de lancer le helloAck", e);
                }
            } else {
                l.error("Un utilisateur existe déjà avec cette adresse IP : "+addr.toString() + " ou le pseudo : " + messHello.getUserName());
            }
        } else {
            l.debug("do nothing not connected");
        }
    }

    public void processHelloAck(HelloAck mess, InetAddress addr)
    {
        if(isConnected())
        {
            RemoteUser n = new RemoteUser(false, mess.getUserName(), addr);
            if(!userExists(n))
            {
                this.addUser(n);
            } else {
                l.error("Un utilisateur existe déjà avec cette adresse IP : " + addr.toString() + " ou le pseudo : " + mess.getUserName());

            }
        } else {
            l.debug("do nothing, not connected");
        }
    }

    public void processRemoteGoodBye(InetAddress addr){
        if(isConnected())
        {
            User u = getUserByAddr(addr);
            if(u != null && u instanceof RemoteUser){
                this.removeUser((RemoteUser) u);
            }
            else{
                l.debug("Unknown " + addr.toString());
            }
        }
        else{
            l.debug("do nothing, not connected");
        }
    }

    public void processMessage(Message message, InetAddress addr) {
        if(isConnected())
        {
            User u = getUserByAddr(addr);
            if(u != null && u instanceof RemoteUser)
            {
                Sound.playSound(Sound.URL_SOUND_MSG);
                this.chatGUI.newMessage(new MessageNetwork((RemoteUser) u, message.getMessageData()));
                // TODO: send messageAck
            } else {
                l.debug("unknown user or local user : " + addr.toString());
            }
        } else {
            l.debug("not connected, do nothing");
        }
    }

    public ArrayList<InetAddress> getNetworkBroadcastAddresses() throws TechnicalException
    {
        return this.chatNI.getNetworkBroadcastAddresses();
    }

    private synchronized void addUser(RemoteUser u)
    {
        this.users.add(u);
        l.debug("New user : " + u.toString());
        chatGUI.addUser(u);
        //chatGUI.addMessage(new MessageSystem(u.getName() + " connected !"));
    }

    private synchronized void removeUser(RemoteUser u)
    {
        this.users.remove(u);
        chatGUI.removeUser(u);
        l.debug("Remove user : " + u.toString());
        //chatGUI.addMessage(new MessageSystem(u.getName() + " disconnected."));
    }

    private synchronized void flushUsers()
    {
        //chatGUI.addMessage(new MessageSystem("You're now disconnected."));
        //chatGUI.setLocalUser(null);
        this.users.clear();
    }

    private boolean userExists(String username)
    {
        return getUserByUsername(username) != null;
    }

    private boolean userExists(InetAddress addr)
    {
        return getUserByAddr(addr) != null;
    }

    private boolean userExists(String username, InetAddress addr)
    {
        return getUserByAddr(addr) != null || getUserByUsername(username) != null;
    }

    private boolean userExists(User u)
    {
        return this.userExists(u.getName(), u.getIp());
    }

    private synchronized User getUserByAddr(InetAddress addr)
    {
        User ret = null;
        for(User u: users)
        {
            if(u.getIp().equals(addr))
            {
                ret = u;
                break;
            }
        }
        return ret;
    }

    private synchronized User getUserByUsername(String username)
    {
        User ret = null;
        for(User u: users)
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
        User ret = null;
        for(User u : users)
        {
            if(u.isLocalUser())
            {
                ret = u;
                break;
            }
        }
        return ret;
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

    public synchronized ArrayList<User> getUsers() {
        return users;
    }

    public void processExit() {
        processDisconnect();
        if(chatNI != null)
            chatNI.exit();
        System.exit(0);
    }

}
