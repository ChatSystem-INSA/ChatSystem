package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class ChatPanel extends JScrollPane {

    public ChatPanel(ChatGUI chatGUI) {

        JList<Message> messageList = new JList<Message>(chatGUI.getListMessage());
        messageList.setLayoutOrientation(JList.VERTICAL);
        messageList.setCellRenderer(new MessageLabel());

        this.setViewportView(messageList);
        this.setPreferredSize(new Dimension(600, 350));
        this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
            public void adjustmentValueChanged(AdjustmentEvent e){
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });

    }
}
