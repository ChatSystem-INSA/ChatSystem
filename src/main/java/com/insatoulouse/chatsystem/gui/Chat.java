package com.insatoulouse.chatsystem.gui;

import com.insatoulouse.chatsystem.model.LocalUser;
import com.insatoulouse.chatsystem.model.MessageNetwork;
import com.insatoulouse.chatsystem.model.RemoteUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Main chat panel
 */
public class Chat {

    /**
     * Logger
     */
    private static final Logger l = LogManager.getLogger(Chat.class.getName());

    /**
     * Facade to communicate with controller
     */
    private ChatGUI chatGUI;

    /**
     * Current conversation user
     * Can be null
     */
    private RemoteUser currentChatuser;

    /**
     * Main panel
     */
    private JPanel panel1;

    /**
     * On right side : message list
     */
    private JList<MessageNetwork> messagelist;

    /**
     * ListModel of messagelist
     * @see this.messagelist
     */
    private DefaultListModel<MessageNetwork> messages = new DefaultListModel<MessageNetwork>();

    private JTextField messageField;
    private JLabel to;

    /**
     * On left side : user list
     */
    private JList<RemoteUser> userlist;

    /**
     * ListModel of userlist
     * @see this.userlist
     */
    private DefaultListModel<RemoteUser> users = new DefaultListModel<RemoteUser>();

    private JButton logOutButton;
    private JLabel nbUser;
    private JLabel username;
    private JButton send;

    /**
     * Initialize default view of chat panel
     *
     * @param chatGUI facade to communicate with controller
     * @param lastusers current users
     */
    public Chat(ChatGUI chatGUI,LocalUser localUser, ArrayList<RemoteUser> lastusers) {
        this.chatGUI = chatGUI;

        disableChat();
        setTo("");
        username.setText(localUser.getName());

        for(RemoteUser u : lastusers){
            users.addElement(u);
        }
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
            public final Color NORMAL = new Color(235, 235, 237);
            public final Color HOVER = new Color(213, 213, 215);
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                logOutButton.setBackground(HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                logOutButton.setBackground(NORMAL);
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
                if(!Chat.this.messageField.getText().equals(""))
                {
                    Chat.this.chatGUI.sendMessage(currentChatuser, Chat.this.messageField.getText());
                    Chat.this.messageField.setText("");
                }
            }
        });
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));

                int r = chooser.showOpenDialog(new JFrame());
                if (r == JFileChooser.APPROVE_OPTION) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Chat.this.chatGUI.sendFile(currentChatuser, chooser.getSelectedFile());
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * To switch message in messagelist when currentChatuser change
     *
     * @see this.currentChatuser
     * @param u new user
     */
    private void switchCurrentChatUser(RemoteUser u){
        if(!u.equals(currentChatuser)){
            messageField.setEnabled(true);
            l.trace("Switch chat to "+u.getName());

            currentChatuser = u;
            messages.removeAllElements();
            for(MessageNetwork m : u.getMessages()){
                messages.addElement(m);
            }
            setTo(u.getName());
            userlist.setSelectedValue(u, true);
        }

    }

    /**
     * Disable chat when we have no remote user
     */
    private void disableChat(){
        currentChatuser = null;
        messageField.setEnabled(false);
        setTo("");
        messages.removeAllElements();
    }

    /**
     * Actualize number of connected user
     */
    private void refreshNbUser(){
        nbUser.setText(users.getSize()+" users connected");
    }

    /**
     * Format string for JLabel To
     * @see this.to
     * @param u string
     */
    private void setTo(String u ){
        to.setText("To: "+u);
    }

    /**
     * New user in userlist
     * @param u user added
     */
    public void addUser(RemoteUser u){
        users.addElement(u);
        if(currentChatuser == null){
            switchCurrentChatUser(u);
        }
    }

    /**
     * Remove user in userlist
     * @param u user deleted
     */
    public void removeUser(RemoteUser u){
        users.removeElement(u);
        if(!users.isEmpty()){
            switchCurrentChatUser(users.get(0));
        }
        else{
            disableChat();
        }
    }

    /**
     * New incoming message
     * @param m incoming message
     */
    public void newMessage(MessageNetwork m ){
        if(m.getUser().equals(currentChatuser)){
            messages.addElement(m);
        }
        userlist.repaint();
        int lastIndex = messages.getSize() - 1;
        if (lastIndex >= 0) {
            messagelist.ensureIndexIsVisible(lastIndex);
        }
    }

    /**
     * Get MainPanel
     * @return JPanel
     */
    public JPanel getPanel() {
        return panel1;
    }

}
