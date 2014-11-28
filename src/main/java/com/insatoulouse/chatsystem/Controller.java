package com.insatoulouse.chatsystem;


import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.model.Hello;
import com.insatoulouse.chatsystem.model.Message;
import com.insatoulouse.chatsystem.model.User;
import com.insatoulouse.chatsystem.ni.ChatNI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.util.ArrayList;

public class Controller {

    private static final Logger l = LogManager.getLogger(Controller.class.getName());

    private ChatGUI chatGUI;
    private ChatNI chatNI;

    private ArrayList<User> users;

    public Controller()
    {
        this.users = new ArrayList<User>();
    }

    public void processHello(Hello messHello, InetAddress addr)
    {
        if(getUserByAddr(addr) != null)
        {
            l.error("Un utilisateur existe déjà avec cette adresse IP : "+addr.toString());
            return;
        }

        User u = new User(false, messHello.getUsername(), addr);
        this.users.add(u);
        l.debug("New user : " + u.toString());

    }

    private User getUserByAddr(InetAddress addr)
    {
        User ret = null;
        for(User u: this.users)
        {
            if(u.getIp().equals(addr))
            {
                ret = u;
                break;
            }
        }
        return ret;
    }

    public void setChatNI(ChatNI chatNI) {
        this.chatNI = chatNI;
    }

    public void setChatGUI(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
    }
}
