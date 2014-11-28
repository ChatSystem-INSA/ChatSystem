package com.insatoulouse.chatsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ChatFrame extends JFrame{

    private ChatGUI chatgui;
    private ChatPanel chatPanel;
    private CommandField commandField;

    public ChatFrame(final ChatGUI chatgui) throws HeadlessException {
        super("ChatSystem");

        this.chatgui = chatgui;
        this.chatPanel = new ChatPanel(chatgui);
        this.commandField = new CommandField(chatgui);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(chatPanel, BorderLayout.CENTER);
        mainPanel.add(commandField, BorderLayout.SOUTH);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("ChatSystem");
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                chatgui.sendExit();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
}
