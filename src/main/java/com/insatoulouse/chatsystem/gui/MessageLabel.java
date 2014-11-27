package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.Message;

import javax.swing.*;
import java.awt.*;

/**
 * Created by david on 27/11/14.
 */
public class MessageLabel extends JLabel implements ListCellRenderer<Message> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Message> list, Message value, int index, boolean isSelected, boolean cellHasFocus) {
        this.setText(value.getData());
        this.setFont(new Font("Sans serif",Font.PLAIN, 15));
        this.setForeground(Color.GRAY);
        this.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        return this;
    }
}
