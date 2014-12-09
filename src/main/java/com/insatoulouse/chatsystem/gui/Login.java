package com.insatoulouse.chatsystem.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.util.ArrayList;

public class Login implements ActionListener{

    private static final Logger l = LogManager.getLogger(Login.class.getName());

    private JPanel panel;
    private JButton buttonOK;
    private JTextField textField1;
    private JComboBox<InetAddress> comboBox1;
    private ChatGUI chatGUI;

    public Login(ChatGUI chatGUI, ArrayList<InetAddress> networkBroadcastAddresses) {
        l.trace("Open dialog connection");

        this.chatGUI = chatGUI;

        textField1.addActionListener(this);
        buttonOK.addActionListener(this);
        DefaultComboBoxModel<InetAddress> model = new DefaultComboBoxModel<InetAddress>();
        for(InetAddress addr : networkBroadcastAddresses) {
            model.addElement(addr);
        }
        comboBox1.setModel(model);

        textField1.requestFocusInWindow();
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chatGUI.sendUsername(textField1.getText(), (InetAddress)comboBox1.getSelectedItem());
        l.trace("Dispose dialog connection");
    }
}
