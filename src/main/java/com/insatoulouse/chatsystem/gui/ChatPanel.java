package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.Message;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class ChatPanel extends JScrollPane {

    public ChatPanel(ChatGUI chatGUI) {

        final JList<Message> messageList = new JList<Message>(chatGUI.getListMessage());
        messageList.setLayoutOrientation(JList.VERTICAL);
        messageList.setCellRenderer(new MessageLabel());
        messageList.setFocusable(false);
        this.setViewportView(messageList);
        this.setFocusable(false);


    }
}
