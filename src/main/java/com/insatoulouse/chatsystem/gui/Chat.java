package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.Message;
import com.insatoulouse.chatsystem.model.MessageNetwork;
import com.insatoulouse.chatsystem.model.RemoteUser;
import com.insatoulouse.chatsystem.model.User;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by david on 06/12/14.
 */
public class Chat {

    private JPanel panel1;
    private JList<MessageNetwork> messagelist;
    private JList<RemoteUser> userlist;
    private JTextField messageField;
    private JButton logOutButton;
    private JLabel nbUser;
    private JLabel to;
    private JLabel username;
    private DefaultListModel<RemoteUser> users = new DefaultListModel<RemoteUser>();
    private DefaultListModel<MessageNetwork> messages = new DefaultListModel<MessageNetwork>();
    private RemoteUser currentChatuser;
    private ChatGUI chatGUI;

    public Chat(ChatGUI chatGUI, User localUser) {
        this.chatGUI = chatGUI;

        setTo("");
        username.setText(localUser.getName());

        users.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                Chat.this.refreshNbUser();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                Chat.this.refreshNbUser();
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                Chat.this.refreshNbUser();
            }
        });
        userlist.setModel(users);
        userlist.setCellRenderer(new ListCellRenderer<RemoteUser>() {
            private UserRow row = new UserRow();
            @Override
            public Component getListCellRendererComponent(JList list, RemoteUser value, int index, boolean isSelected, boolean cellHasFocus) {
                if(value.getIp() != null){
                    row.setIp(value.getIp().toString());
                }
                else
                    row.setIp("");
                row.isSelected(isSelected);
                MessageNetwork m = value.getLastMessage();
                row.setLastMessage((m !=null) ? m.getMessage() : "");
                row.setName(value.getName());

                if(isSelected){
                    switchCurrentChatUser(value);
                }

                return row.getPanel();
            }
        });
        refreshNbUser();

        logOutButton.setBorder(null);
        logOutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                logOutButton.setBackground(new Color(213, 213, 215));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                logOutButton.setBackground(new Color(235, 235, 237));
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chat.this.chatGUI.sendLogout();
            }
        });

        messagelist.setModel(messages);
        messagelist.setCellRenderer(new ListCellRenderer<MessageNetwork>() {
            private MessageRow row = new MessageRow();
            @Override
            public Component getListCellRendererComponent(JList<? extends MessageNetwork> list, MessageNetwork value, int index, boolean isSelected, boolean cellHasFocus) {
                row.setTextMessage(value);
                return row.getPanel();
            }
        });

        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chat.this.chatGUI.sendMessage(currentChatuser, Chat.this.messageField.getText());
                Chat.this.messageField.setText("");
            }
        });
    }

    public void addUser(RemoteUser u){
        users.addElement(u);
        if(currentChatuser == null){
            switchCurrentChatUser(u);
        }
    }

    public void removeUser(RemoteUser u){
        users.removeElement(u);
        if(!users.isEmpty()){
            switchCurrentChatUser(users.get(0));
        }
    }

    public void newMessage(MessageNetwork m ){
        if(m.getUser().equals(currentChatuser)){
            messages.addElement(m);
        }
        userlist.repaint();
    }
    public JPanel getPanel() {
        return panel1;
    }

    private void switchCurrentChatUser(RemoteUser u){
        if(!u.equals(currentChatuser)){
            currentChatuser = u;
            messages.removeAllElements();
            for(MessageNetwork m : u.getMessages()){
                messages.addElement(m);
            }
            setTo(u.getName());
            userlist.setSelectedValue(u, true);
        }

    }

    private void refreshNbUser(){
        nbUser.setText(users.getSize()+" users connected");
    }

    private void setTo(String u ){
        to.setText("To: "+u);
    }

    public static void main(String[] args) {

    }
}
