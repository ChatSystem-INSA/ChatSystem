package com.insatoulouse.chatsystem.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.util.ArrayList;

public class Login extends JDialog {

    private static final Logger l = LogManager.getLogger(Login.class.getName());

    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField textField1;
    private JComboBox<InetAddress> comboBox1;
    private ChatGUI chatGUI;

    public Login(ChatGUI chatGUI, ArrayList<InetAddress> networkBroadcastAddresses) {
        l.trace("Open dialog connection");

        this.chatGUI = chatGUI;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        DefaultComboBoxModel<InetAddress> model = new DefaultComboBoxModel<InetAddress>();
        for(InetAddress addr : networkBroadcastAddresses) {
            model.addElement(addr);
        }
        comboBox1.setModel(model);
        this.addWindowListener(chatGUI);
    }

    private void onOK() {
        chatGUI.sendUsername(textField1.getText());
        l.trace("Dispose dialog connection");
        dispose();
    }



}
