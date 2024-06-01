package ru.nsu.chaiko.distributedsystem.threads;

import ru.nsu.chaiko.distributedsystem.ProblemClient;

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