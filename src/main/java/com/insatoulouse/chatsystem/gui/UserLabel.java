package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by david on 27/11/14.
 */
public class UserLabel extends JPanel implements ListCellRenderer<User> {

    private JLabel name = new JLabel();
    private JLabel ip = new JLabel();
    private JPanel milieu = new JPanel();
    public static final Color colors[] = {Color.GREEN, Color.RED, Color.BLUE};

    public UserLabel() {

        this.setLayout(new BorderLayout());
        // this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // this.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // this.setBou

        milieu.setLayout(new BoxLayout(milieu, BoxLayout.Y_AXIS));
        milieu.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        milieu.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

        name.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        name.setFont(new Font("Sans serif", Font.BOLD, 12));
        name.setForeground(Color.white);
        milieu.add(name);

        ip.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        ip.setFont(new Font("Sans serif", Font.BOLD, 11));
        ip.setForeground(Color.white);
        milieu.add(ip);

        this.add(milieu);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected, boolean cellHasFocus) {
        name.setText(value.getName());
        ip.setText(value.getIp().toString());
        milieu.setBackground(UserLabel.colors[index%UserLabel.colors.length]);

        return this;
    }
}
