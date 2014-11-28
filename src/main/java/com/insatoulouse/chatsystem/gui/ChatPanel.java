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
        messageList.getModel().addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ChatPanel.this.getVerticalScrollBar().setValue(ChatPanel.this.getMaximumSize().height);

                    }
                });
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ChatPanel.this.getVerticalScrollBar().setValue(ChatPanel.this.getMaximumSize().height);

                    }
                });
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ChatPanel.this.getVerticalScrollBar().setValue(ChatPanel.this.getMaximumSize().height);

                    }
                });
            }
        });
        this.setViewportView(messageList);


    }
}
