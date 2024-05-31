package ru.nsu.chaiko.distributedSystem.threads;

import java.util.List;
import ru.nsu.chaiko.distributedSystem.net.Server;

/**
 * Thread for servers.
 */
public class ServerThread extends Thread {
    private boolean result;
    private final List<Integer> numbers;

    /**
     * constr.
     */
    public ServerThread(List<Integer> numbers) {
        this.numbers = numbers;
    }

    /**
     * start.
     */
    @Override
    public void run() {
        result = new Server(numbers).start();
    }

    /**
     * getter.
     */
    public boolean getRes() {
        return result;
    }
}
