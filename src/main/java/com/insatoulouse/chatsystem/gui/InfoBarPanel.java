package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.User;

import javax.swing.*;
import java.awt.*;

public class InfoBarPanel extends JPanel {
    private final JLabel name;
    private final JLabel ip;

    public InfoBarPanel() {
        super(new FlowLayout(FlowLayout.RIGHT));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setAlignmentX(JPanel.RIGHT_ALIGNMENT);

        name = new JLabel("Not connected");
        name.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
        name.setFont(new Font("Sans serif", Font.BOLD, 12));
        panel.add(name);
        ip = new JLabel("");
        ip.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
        panel.add(ip);

        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        add(panel);
    }

    public void setUser(final User user) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(user != null){
                    name.setText(user.getName());
                    ip.setText(user.getIp().toString());
                }
                else {
                    name.setText("Not connected");
                    ip.setText("");
                }
            }
        });
    }


}
