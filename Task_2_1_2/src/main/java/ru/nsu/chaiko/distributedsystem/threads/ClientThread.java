package ru.nsu.chaiko.distributedsystem.threads;

import ru.nsu.chaiko.distributedsystem.net.Client;

/**
 * thread for clients.
 */
public class ClientThread extends Thread {
    @Override
    public void run() {
        new Client().start();
    }
}
