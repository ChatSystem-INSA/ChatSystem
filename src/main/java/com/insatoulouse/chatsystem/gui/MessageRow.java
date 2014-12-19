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

import com.insatoulouse.chatsystem.model.MessageNetwork;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Format message row on JList
 */
public class MessageRow {

    private JPanel panel1;

    /**
     * Text message color in grey (local message) or in red (remote message)
     */
    private JTextArea textMessage;

    public JPanel getPanel() {
        return panel1;
    }

    /**
     * Parametrize row view with message
     *
     * @param m message to display
     */
    public void setTextMessage(final MessageNetwork m) {
        textMessage.setText(m.getMessage());
        if (m.getType() == MessageNetwork.IN) {
            textMessage.setBackground(new Color(255, 153, 153));
            panel1.setBorder(new EmptyBorder(5, 5, 5, 40));
        } else {
            textMessage.setBackground(new Color(195, 195, 198));
            panel1.setBorder(new EmptyBorder(5, 40, 5, 5));
        }
    }
}
