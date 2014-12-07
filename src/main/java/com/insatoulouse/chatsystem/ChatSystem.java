package com.insatoulouse.chatsystem;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.model.MessageSystem;
import com.insatoulouse.chatsystem.ni.ChatNI;

import java.util.Random;

public class ChatSystem {


    public ChatSystem()
    {

        Controller c = new Controller();
        try {
            ChatNI ni = new ChatNI(c);
            ChatGUI gui = new ChatGUI(c);
            c.setChatNI(ni);
            c.setChatGUI(gui);
        } catch (TechnicalException e) {
            e.printStackTrace();
        }
    }

}
