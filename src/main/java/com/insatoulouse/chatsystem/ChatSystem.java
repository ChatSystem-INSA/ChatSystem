package com.insatoulouse.chatsystem;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.gui.ChatGUI;
import com.insatoulouse.chatsystem.model.Message;
import com.insatoulouse.chatsystem.ni.ChatNI;
import com.insatoulouse.chatsystem.utils.Config;

import java.io.IOException;
import java.util.Random;

public class ChatSystem {


    public ChatSystem()
    {

        Controller c = new Controller();
        ChatGUI gui = new ChatGUI(c);
        try {
            ChatNI ni = new ChatNI(c);
            throw new TechnicalException();
            //c.setChatNI(ni);
        } catch (TechnicalException e) {
            String[] nom = {"Mickael", "Hugo", "Jérémie","David","Alfred","Mr Exposito","ta maman"};
            Random r = new Random();
            gui.addMessage(new Message("Une erreur fortuite vient de se produire par la faute de "+nom[r.nextInt(nom.length)]+". Merci de lui réserver le châtiment à la mesure de votre déconvenue."));
        }
        c.setChatGUI(gui);
    }

}
