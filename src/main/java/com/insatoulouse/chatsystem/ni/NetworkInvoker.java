package com.insatoulouse.chatsystem.ni;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NetworkInvoker {
    private LinkedList<NetworkCommand> commands = new LinkedList<NetworkCommand>();

    public NetworkInvoker() {
    }

    public void addCommand(NetworkCommand command){
        commands.add(command);
    }

}
