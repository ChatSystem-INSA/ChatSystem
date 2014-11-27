package com.insatoulouse.chatsystem.ni;


public class TcpSenderCommand implements NetworkCommand {

    private TcpSender tcpSender;

    public TcpSenderCommand(TcpSender tcpSender) {
        this.tcpSender = tcpSender;
    }

    @Override
    public void execute() {
        // TODO implement method
    }
}
