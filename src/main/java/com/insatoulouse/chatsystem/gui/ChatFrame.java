package com.insatoulouse.chatsystem.gui;

import javax.swing.*;
import java.awt.*;

public class ChatFrame extends JFrame{

    private ChatGUI chatgui;
    private ChatPanel chatPanel;
    private CommandField commandField;

    public ChatFrame(ChatGUI chatgui) throws HeadlessException {
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
    }
}
