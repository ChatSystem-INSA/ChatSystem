/*
 * Chat System - P2P
 *     Copyright (C) 2014 LIVET BOUTOILLE
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * NetworkInvoker class
 */
public class NetworkInvoker extends Thread {

    private static final Logger l = LogManager.getLogger(NetworkInvoker.class.getName());
    private static final Integer MAX_COMMAND = 5;
    private final LinkedBlockingQueue<NetworkCommand> commands = new LinkedBlockingQueue<NetworkCommand>();
    private Integer onRun = 0;
    private Boolean isRunning = true;

    public NetworkInvoker() {
    }

    public void run() {
        while (isRunning) {
            try {
                final NetworkCommand cmd = commands.peek();

                synchronized (onRun) {
                    if (onRun < MAX_COMMAND && cmd != null) {

                        onRun++;

                        l.debug("New command executed :" + cmd);
                        commands.poll();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    cmd.execute();
                                } catch (TechnicalException e) {
                                    e.printStackTrace();
                                }
                                NetworkInvoker.this.endOfCommand();
                            }
                        }).start();
                    } else {
                        synchronized (this) {
                            this.wait();
                        }
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    private synchronized void endOfCommand() {
        onRun--;
        this.interrupt();
    }

    public synchronized void addCommand(NetworkCommand command) {
        commands.add(command);
        this.interrupt();
    }

    public synchronized void close() {
        isRunning = false;
        this.interrupt();
    }
}
