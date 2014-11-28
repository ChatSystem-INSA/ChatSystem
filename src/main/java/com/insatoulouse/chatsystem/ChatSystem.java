package com.insatoulouse.chatsystem;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.ni.ChatNI;
import com.insatoulouse.chatsystem.utils.Config;

import java.io.IOException;

public class ChatSystem {


    public ChatSystem()
    {

        Controller c = new Controller();
        ChatGUI gui = new ChatGUI(c);
        try {
            Config config = Config.getInstance();
            String port = config.getProperties("port");
            ChatNI ni = new ChatNI(c,Integer.parseInt(port));
        } catch (TechnicalException e) {
            //TODO informer le GUI du problème
        }
    }

}
