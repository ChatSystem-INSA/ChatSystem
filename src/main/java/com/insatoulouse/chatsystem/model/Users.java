package com.insatoulouse.chatsystem.model;


import java.util.ArrayList;
import java.util.Observable;

public class Users extends Observable{
    private ArrayList<User> users = new ArrayList<User>();

    public void addUser(User e ){
        users.add(e);
        notifyObservers();
    }

    public ArrayList<User> getUsers(){
        return users;
    }
}
