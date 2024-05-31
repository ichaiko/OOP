package ru.nsu.chaiko.distributedSystem.threads;

import ru.nsu.chaiko.distributedSystem.ProblemClient;

/**
 * problemClientThread.
 */
public class ProblemClientThread extends Thread {

    /**
     * start.
     */
    @Override
    public void run() {
        new ProblemClient().start();
    }
}