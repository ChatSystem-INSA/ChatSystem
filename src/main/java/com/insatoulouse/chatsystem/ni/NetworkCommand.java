package com.insatoulouse.chatsystem.ni;


import com.insatoulouse.chatsystem.exception.TechnicalException;

public interface NetworkCommand {

    public void execute() throws TechnicalException;
}
