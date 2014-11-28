package com.insatoulouse.chatsystem;

import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.ni.ChatNI;

import java.io.IOException;

public class ChatSystem {

    public ChatSystem()
    {
        Controller c = new Controller();
        ChatGUI gui = new ChatGUI(c);
        ChatNI ni = new ChatNI(c,1337);
    }

}
