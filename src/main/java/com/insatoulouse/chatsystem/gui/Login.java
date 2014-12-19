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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Login view
 */
public class Login implements ActionListener {

    /**
     * Logger
     */
    private static final Logger l = LogManager.getLogger(Login.class.getName());
    /**
     * Facade to communicate with controller
     */
    private final ChatGUI chatGUI;
    private JPanel panel;
    private JButton buttonOK;
    /**
     * Username text field
     */
    private JTextField textField1;
    /**
     * List of broadcast address to chose network
     */
    private JComboBox<InetAddress> comboBox1;

    public Login(ChatGUI chatGUI, ArrayList<InetAddress> networkBroadcastAddresses) {
        l.trace("Open dialog connection");

        this.chatGUI = chatGUI;

        textField1.addActionListener(this);
        buttonOK.addActionListener(this);
        DefaultComboBoxModel<InetAddress> model = new DefaultComboBoxModel<InetAddress>();
        for (InetAddress addr : networkBroadcastAddresses) {
            model.addElement(addr);
        }
        comboBox1.setModel(model);

        textField1.requestFocusInWindow();
    }

    public JPanel getPanel() {
        return panel;
    }

    /**
     * Performed connection
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!textField1.getText().isEmpty()) {
            textField1.setEnabled(false);
            buttonOK.setEnabled(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    chatGUI.sendUsername(textField1.getText(), (InetAddress) comboBox1.getSelectedItem());
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            textField1.setEnabled(true);
                            buttonOK.setEnabled(true);
                        }
                    });
                }
            }).start();
            l.trace("Dispose dialog connection");
        }
    }
}
