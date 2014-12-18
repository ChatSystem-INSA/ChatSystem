/*
 * Chat System - P2P
 *     Copyright (C) 2014 LIVET BOUTOILLE
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.insatoulouse.chatsystem.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Format user row on JList
 */
public class UserRow {

    /**
     * Display last message of conversation
     */
    private JLabel lastMessage;

    /**
     * Ip address of remote user
     */
    private JLabel ip;

    /**
     * Name of remote user
     */
    private JLabel name;

    private JPanel panel;
    private JPanel panelInner;

    public JPanel getPanel() {
        return panel;
    }

    public void setIp(String ip) {
        this.ip.setText(ip);
    }

    public JLabel getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage.setText(lastMessage);
    }

    public void isSelected(Boolean selected) {
        if (selected) {
            panelInner.setBackground(new Color(213, 213, 215));
            lastMessage.setBackground(new Color(213, 213, 215));
        } else {
            panelInner.setBackground(new Color(235, 235, 237));
            lastMessage.setBackground(new Color(235, 235, 237));
        }
    }

}
