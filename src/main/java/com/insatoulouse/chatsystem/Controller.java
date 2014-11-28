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
        if(getUserByAddr(addr) != null || getUserByUsername(messHello.getUserName()) != null)
        {
            l.error("Un utilisateur existe déjà avec cette adresse IP : "+addr.toString() + " ou le pseudo : " + messHello.getUserName());
            return;
        }

        User u = new User(false, messHello.getUserName(), addr);
        this.users.add(u);
        l.debug("New user : " + u.toString());
        chatGUI.addMessage(new Message("New user : "+u.getName()));
    }

    public void processHelloAck(HelloAck mess, InetAddress addr)
    {
        if(isConnected())
        {
            User n = new User(false, mess.getUserName(), addr);
            this.users.add(n);
            l.debug("New user : " + n.toString());
            chatGUI.addMessage(new Message("New user : " + n.getName()));
        }
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
