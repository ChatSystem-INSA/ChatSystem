package com.insatoulouse.chatsystem.gui;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class CommandField extends JTextField {
    public CommandField(final ChatGUI chatGUI) {
        this.setBorder(BorderFactory.createCompoundBorder(
                this.getBorder(),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        this.setFont(new Font("Sans serif",Font.PLAIN,20));
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommandField.this.setEditable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(!CommandField.this.getText().isEmpty()) {
                            chatGUI.executeCommand(CommandField.this.getText());
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                CommandField.this.setText("");
                                CommandField.this.setEditable(true);
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
