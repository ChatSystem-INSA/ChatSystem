package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Created by david on 27/11/14.
 */
public class UserLabel extends JLabel implements ListCellRenderer<User> {
    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected, boolean cellHasFocus) {
        this.setText("tata");
        return this;
    }
}
