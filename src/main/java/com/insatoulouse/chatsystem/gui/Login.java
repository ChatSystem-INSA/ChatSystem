package com.insatoulouse.chatsystem.gui;

import javax.swing.*;
import java.awt.event.*;

public class Login extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField textField1;
    private ChatGUI chatGUI;

    public Login(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        chatGUI.sendUsername(textField1.getText());
        dispose();
    }

}
