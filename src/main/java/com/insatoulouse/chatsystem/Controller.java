package com.insatoulouse.chatsystem;


import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.model.*;
import com.insatoulouse.chatsystem.ni.ChatNI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Controller {

    private static final Logger l = LogManager.getLogger(Controller.class.getName());

    private ChatGUI chatGUI;
    private ChatNI chatNI;

    private ArrayList<User> users = new ArrayList<User>();

    public Controller()
    {
    }

    public void processConnection(String username)
    {
        if(isConnected())
        {
            l.error("Déjà connecté.");
            return;
        }

        try {
            User local = new User(true, username, InetAddress.getLocalHost());
            l.debug("Connection de l'utilisateur local : " + local);
            this.chatNI.sendHello(local);
            this.users.add(local);
            chatGUI.addMessage(new MessageSystem("Now connected as " + username + " !"));
            chatGUI.setLocalUser(local);
        } catch (UnknownHostException e) {
            l.error("Impossible d'obtenir l'adresse locale");
        } catch (TechnicalException e) {
            chatGUI.addMessage(new MessageSystem("Unable to connect ..."));
            l.error("unable to connect", e);
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
        }
    }

    public void processHello(Hello messHello, InetAddress addr)
    {
        if(isConnected())
        {
            User u = new User(false, messHello.getUserName(), addr);
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
            User n = new User(false, mess.getUserName(), addr);
            if(!userExists(n))
            {
                this.addUser(n);
            } else {
                l.error("Un utilisateur existe déjà avec cette adresse IP : "+addr.toString() + " ou le pseudo : " + mess.getUserName());

            }
        } else {
            l.debug("do nothing, not connected");
        }
    }

    public void processRemoteGoodBye(InetAddress addr){
        if(isConnected())
        {
            User u = getUserByAddr(addr);
            if(u != null){
                this.removeUser(u);
            }
            else{
                l.debug("Unknown "+addr.toString());
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
            if(u != null)
            {
                this.chatGUI.addMessage(new MessageNetwork(u, message.getMessageData()));
                // TODO: send messageAck
            } else {
                l.debug("Unknow "+addr.toString());
            }
        } else {
            l.debug("not connected, do nothing");
        }
    }

    private void addUser(User u)
    {
        this.users.add(u);
        l.debug("New user : " + u.toString());
        chatGUI.addMessage(new MessageSystem(u.getName() + " connected !"));
    }

    private void removeUser(User u)
    {
        this.users.remove(u);
        l.debug("Remove user : " + u.toString());
        chatGUI.addMessage(new MessageSystem(u.getName() + " disconnected."));
    }

    private void flushUsers()
    {
        chatGUI.addMessage(new MessageSystem("You're now disconnected."));
        chatGUI.setLocalUser(null);
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

    private User getUserByAddr(InetAddress addr)
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

    private User getUserByUsername(String username)
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

    private User getLocalUser()
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public void processExit() {
        processDisconnect();
        chatNI.exit();
        System.exit(0);
    }

}
