package com.insatoulouse.chatsystem;

import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.ni.ChatNI;

import java.io.IOException;

/**
 * Created by tlk on 27/11/14.
 */
public class ChatSystem {

    public ChatSystem()
    {
        ChatGUI gui = new ChatGUI();
        try {
            ChatNI ni = new ChatNI(1337);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
