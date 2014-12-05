package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.DisplayMessage;
import com.insatoulouse.chatsystem.model.Message;
import com.insatoulouse.chatsystem.model.MessageNetwork;
import com.insatoulouse.chatsystem.model.MessageSystem;

import javax.swing.*;
import java.awt.*;

/**
 * Created by david on 27/11/14.
 */
public class MessageLabel extends JLabel implements ListCellRenderer<DisplayMessage> {
    @Override
    public Component getListCellRendererComponent(JList<? extends DisplayMessage> list, DisplayMessage value, int index, boolean isSelected, boolean cellHasFocus) {
        if(value instanceof MessageSystem)
        {
            MessageSystem m = (MessageSystem) value;
            this.setText(m.getData());
            this.setFont(new Font("Sans serif",Font.ITALIC, 15));
            if(m.getType() == MessageSystem.INFO)
            {
                this.setForeground(Color.BLUE);
            } else {
                this.setForeground(Color.RED);
            }
            this.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        } else if(value instanceof MessageNetwork)
        {
            MessageNetwork m = (MessageNetwork) value;

            if(m.getType() == MessageNetwork.OUT)
            {
                this.setText(">>> "+m.getUser().getName()+" : "+m.getMessage());
                this.setForeground(Color.ORANGE);
            } else {
                this.setText("<"+m.getUser().getName()+"> "+m.getMessage());
                this.setForeground(Color.BLACK);
            }

            this.setFont(new Font("Sans serif", Font.PLAIN, 12));
            this.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        }

        return this;
    }
}
