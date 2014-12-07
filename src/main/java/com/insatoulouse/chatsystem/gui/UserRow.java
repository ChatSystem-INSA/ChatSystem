package com.insatoulouse.chatsystem.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by david on 07/12/14.
 */
public class UserRow {
    private JLabel lastMessage;
    private JLabel ip;
    private JLabel name;
    private JPanel panel;
    private JPanel panelInner;

    public JPanel getPanel() {
        return panel;
    }

    public void setIp(String ip) {
        this.ip.setText(ip);
    }

    public JLabel getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage.setText(lastMessage);
    }

    public void isSelected(Boolean selected){
        if(selected){
            panelInner.setBackground(new Color(213, 213, 215));
            lastMessage.setBackground(new Color(213, 213, 215));
        }
        else {
            panelInner.setBackground(new Color(235, 235,237));
            lastMessage.setBackground(new Color(235, 235,237));
        }
    }

}
