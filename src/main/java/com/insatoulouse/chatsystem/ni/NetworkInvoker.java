package com.insatoulouse.chatsystem.ni;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

public class NetworkInvoker extends Thread {

    private static final Logger l = LogManager.getLogger(NetworkInvoker.class.getName());
    private static final Integer MAX_COMMAND = 5;

    private Integer onRun = 0;
    private LinkedBlockingQueue<NetworkCommand> commands = new LinkedBlockingQueue<NetworkCommand>();
    private Boolean isRunning = true;

    public NetworkInvoker() {
    }

    public void run()
    {
        while(isRunning)
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
                        }).start();
                    } else {
                        synchronized (this)
                        {
                            this.wait();
                        }
                    }
                }
            } catch (InterruptedException ignored) {}
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

    public synchronized void close() {
        isRunning = false;
        this.interrupt();
    }
}
