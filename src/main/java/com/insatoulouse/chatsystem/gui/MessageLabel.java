package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.MessageSystem;

import javax.swing.*;
import java.awt.*;

/**
 * Created by david on 27/11/14.
 */
public class MessageLabel extends JLabel implements ListCellRenderer<MessageSystem> {
    @Override
    public Component getListCellRendererComponent(JList<? extends MessageSystem> list, MessageSystem value, int index, boolean isSelected, boolean cellHasFocus) {
        this.setText(value.getData());
        this.setFont(new Font("Sans serif",Font.PLAIN, 15));
        this.setForeground(Color.GRAY);
        this.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        return this;
    }
}
