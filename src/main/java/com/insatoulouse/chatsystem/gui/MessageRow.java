package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.FileNetwork;
import com.insatoulouse.chatsystem.model.MessageNetwork;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Format message row on JList
 */
public class MessageRow {

    private JPanel panel1;
    private JTextArea textMessage;

    public JPanel getPanel() {
        return panel1;
    }

    public void setTextMessage(final MessageNetwork m){
        textMessage.setText(m.getMessage());
        if(m.getType() == MessageNetwork.IN){
            textMessage.setBackground(new Color(255,153,153));
            panel1.setBorder(new EmptyBorder(5, 5, 5, 40));
        }
        else{
            textMessage.setBackground(new Color(195, 195, 198));
            panel1.setBorder(new EmptyBorder(5,40,5,5));
        }
    }
}
