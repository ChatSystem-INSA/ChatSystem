package com.insatoulouse.chatsystem;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.gui.Chat;
import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.model.MessageSystem;
import com.insatoulouse.chatsystem.model.User;
import com.insatoulouse.chatsystem.ni.ChatNI;

import javax.swing.*;
import java.util.Random;

public class ChatSystem {


    public ChatSystem()
    {

        Controller c = new Controller();
        try {
            ChatNI ni = new ChatNI(c);
            c.setChatNI(ni);
        } catch (TechnicalException e) {
            //String[] nom = {"Mickael", "Hugo", "Jérémie","David","Alfred","Mr Exposito","ta maman"};
            //Random r = new Random();
            //gui.addMessage(new MessageSystem("Une erreur fortuite vient de se produire par la faute de "+nom[r.nextInt(nom.length)]+". Merci de lui réserver le châtiment à la mesure de votre déconvenue."));
        }
        new ChatGUI(c);
    }

}
