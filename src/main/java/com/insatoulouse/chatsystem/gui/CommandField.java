package com.insatoulouse.chatsystem.gui;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class CommandField extends JTextField {
    private ArrayList<String> historyCommand = new ArrayList<String>();
    private int index = 0;

    public CommandField(final ChatGUI chatGUI) {
        this.setBorder(BorderFactory.createCompoundBorder(
                this.getBorder(),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)));
        this.setFont(new Font("Sans serif",Font.PLAIN,20));
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CommandField.this.isEditable()){
                    CommandField.this.setEditable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(!CommandField.this.getText().isEmpty()) {
                                String text = CommandField.this.getText();
                                chatGUI.executeCommand(text);
                                historyCommand.add(text);
                                index = historyCommand.size()-1;
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
            }
        });
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP:
                        CommandField.this.setText(historyCommand.get(index));
                        if(index>0)
                            index--;
                        break;
                    case KeyEvent.VK_DOWN:
                        if(index == historyCommand.size())
                            CommandField.this.setText("");
                        else
                            CommandField.this.setText(historyCommand.get(index));
                        if(index<historyCommand.size())
                            index++;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
