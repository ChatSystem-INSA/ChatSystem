package com.insatoulouse.chatsystem.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.*;

public class Login extends JDialog {

    private static final Logger l = LogManager.getLogger(Login.class.getName());

    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField textField1;
    private ChatGUI chatGUI;

    public Login(ChatGUI chatGUI) {
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
        this.addWindowListener(chatGUI);
    }

    private void onOK() {
        chatGUI.sendUsername(textField1.getText());
        dispose();
    }

}
