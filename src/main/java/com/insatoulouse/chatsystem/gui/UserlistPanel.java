package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.Message;
import com.insatoulouse.chatsystem.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Created by rusty on 28/11/2014.
 */
public class UserlistPanel extends JScrollPane {
    public UserlistPanel(ChatGUI chatGUI) {
        JList<User> userList = new JList<User>(chatGUI.getListUser());
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setCellRenderer(new UserLabel());

        this.setViewportView(userList);
        this.setPreferredSize(new Dimension(600, 350));
        this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
            public void adjustmentValueChanged(AdjustmentEvent e){
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });
    }
}
