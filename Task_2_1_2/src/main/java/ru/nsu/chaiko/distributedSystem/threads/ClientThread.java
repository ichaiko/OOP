package ru.nsu.chaiko.distributedSystem.threads;

import ru.nsu.chaiko.distributedSystem.net.Client;

/**
 * thread for clients.
 */
public class ClientThread extends Thread {
    @Override
    public void run() {
        new Client().start();
    }
}
