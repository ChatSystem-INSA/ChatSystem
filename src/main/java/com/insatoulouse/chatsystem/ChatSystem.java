package com.insatoulouse.chatsystem;

import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.ni.ChatNI;

public class ChatSystem {


    public ChatSystem()
    {
        Controller c = new Controller();
        ChatNI ni = new ChatNI(c);
        c.setChatNI(ni);
        new ChatGUI(c);
    }

}
