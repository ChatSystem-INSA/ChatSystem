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
            chatGUI.addMessage(new Message("Now connected as " + username + " !"));
        } catch (UnknownHostException e) {
            l.error("Impossible d'obtenir l'adresse locale");
        } catch (TechnicalException e) {
            chatGUI.addMessage(new Message("Unable to connect ..."));
            l.error("unable to connect", e);
        }

    }

    public void processHello(Hello messHello, InetAddress addr)
    {

        User u = new User(false, messHello.getUserName(), addr);

        if(isConnected())
        {
            if(userExists(u))
            {
                l.error("Un utilisateur existe déjà avec cette adresse IP : "+addr.toString() + " ou le pseudo : " + messHello.getUserName());
                return;
            }
            this.addUser(u);
        } else {
            l.debug("do nothing not connected");
        }
    }

    public void processHelloAck(HelloAck mess, InetAddress addr)
    {
        if(isConnected())
        {
            User n = new User(false, mess.getUserName(), addr);
            if(userExists(n))
            {
                l.error("Un utilisateur existe déjà avec cette adresse IP : "+addr.toString() + " ou le pseudo : " + mess.getUserName());
                return;
            }
            this.addUser(n);
        } else {
            l.debug("do nothing, not connected");
        }
    }

    private void addUser(User u)
    {
        this.users.add(u);
        l.debug("New user : " + u.toString());
        chatGUI.addMessage(new Message("New user : " + u.getName()));
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

    private boolean isConnected()
    {
        return this.getLocalUser() != null;
    }

    public void setChatNI(ChatNI chatNI) {
        this.chatNI = chatNI;
    }

    public void setChatGUI(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
    }

    public void processExit() {
        chatNI.exit();
        System.exit(0);
    }
}
