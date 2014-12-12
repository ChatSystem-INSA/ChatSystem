package com.insatoulouse.chatsystem.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        textField1.setEnabled(false);
        buttonOK.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                chatGUI.sendUsername(textField1.getText(), (InetAddress)comboBox1.getSelectedItem());
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
