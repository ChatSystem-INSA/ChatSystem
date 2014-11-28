package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.Message;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JScrollPane {

    public ChatPanel(ChatGUI chatGUI) {

        JList<Message> messageList = new JList<Message>(chatGUI.getListMessage());
        messageList.setLayoutOrientation(JList.VERTICAL);
        messageList.setCellRenderer(new MessageLabel());

        this.setViewportView(messageList);
        this.setPreferredSize(new Dimension(600, 350));
    }
}
