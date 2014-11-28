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
            ChatNI ni = new ChatNI(c,Integer.parseInt(Config.getInstance().getProperties(Config.CONFIG_PORT)));
            c.setChatNI(ni);
        } catch (TechnicalException e) {
            //TODO informer le GUI du problème
        }
        c.setChatGUI(gui);
    }

}
