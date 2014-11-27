package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.Message;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JScrollPane {

    public ChatPanel() {
        Message test1 = new Message("toto");
        Message test2 = new Message("tata");

        DefaultListModel<Message> list = new DefaultListModel<Message>();
        list.add(0,test1);
        list.add(0,test2);


        JList<Message> messageList = new JList<Message>(list);
        messageList.setLayoutOrientation(JList.VERTICAL);
        messageList.setCellRenderer(new MessageLabel());

        this.setViewportView(messageList);
        this.setPreferredSize(new Dimension(600, 350));
    }
}
