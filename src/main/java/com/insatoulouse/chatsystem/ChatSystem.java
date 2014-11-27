package com.insatoulouse.chatsystem;

import com.insatoulouse.chatsystem.ni.ChatNI;

import java.io.IOException;

/**
 * Created by tlk on 27/11/14.
 */
public class ChatSystem {

    public ChatSystem()
    {
        try {
            ChatNI ni = new ChatNI(1337);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
