package com.insatoulouse.chatsystem.ni;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

public class NetworkInvoker extends Thread {

    private static final Logger l = LogManager.getLogger(ChatNI.class.getName());
    private static final Integer MAX_COMMAND = 5;

    private Integer onRun = 0;
    private LinkedBlockingQueue<NetworkCommand> commands = new LinkedBlockingQueue<NetworkCommand>();

    public NetworkInvoker() {
    }

    public void run()
    {
        while(true)
        {
            try {
                final NetworkCommand cmd = commands.peek();

                synchronized (onRun) {
                    if (onRun < MAX_COMMAND && cmd != null){

                        onRun++;

                        l.debug("New command executed :" + cmd);
                        commands.poll();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cmd.execute();
                                NetworkInvoker.this.endOfCommand();
                            }
                        });
                    } else {
                        synchronized (this)
                        {
                            this.wait();
                        }
                    }
                }
            } catch (InterruptedException e) {}
        }
    }

    private synchronized void endOfCommand() {
        onRun--;
        this.interrupt();
    }

    public synchronized void addCommand(NetworkCommand command){
        commands.add(command);
        this.interrupt();
    }

}
