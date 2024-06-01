import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.chaiko.distributedsystem.threads.ClientThread;
import ru.nsu.chaiko.distributedsystem.threads.ProblemClientThread;
import ru.nsu.chaiko.distributedsystem.threads.ServerThread;

/**
 * Main test class.
 */
public class MainTest {
    @Test
    public void goodClients() {
        List<Integer> numbers = List.of(6997901, 6997927, 6997937, 17858849,
            6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        ServerThread server = new ServerThread(numbers);
        ClientThread client1 = new ClientThread();
        ClientThread client2 = new ClientThread();
        ClientThread client3 = new ClientThread();

        server.start();
        client1.start();
        client2.start();
        client3.start();

        try {
            client1.join();
            client2.join();
            client3.join();
            server.join();
            Assertions.assertFalse(server.getRes());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void problemClientAndOneGoodClient() {
        List<Integer> numbers = List.of(20319251, 6997937, 17858849,
            6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        ServerThread server = new ServerThread(numbers);
        ProblemClientThread client1 = new ProblemClientThread();
        ClientThread client2 = new ClientThread();

        server.start();
        client2.start();
        client1.start();

        try {
            client2.join();
            client1.join();
            server.join();
            Assertions.assertFalse(server.getRes());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void noClients() {
        List<Integer> numbers = List.of(1, 6, 8, 7, 5, 9, 4);
        ServerThread server = new ServerThread(numbers);

        server.start();

        try {
            server.join();
            Assertions.assertTrue(server.getRes());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void onlyProblemClient() {
        List<Integer> numbers = List.of(6, 8, 7, 13, 5, 9, 4);
        ServerThread server = new ServerThread(numbers);
        ProblemClientThread client = new ProblemClientThread();

        server.start();
        client.start();

        try {
            client.join();
            server.join();
            Assertions.assertTrue(server.getRes());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void badClients() {
        List<Integer> numbers = List.of(20319251, 6997901, 6997927, 6997937, 17858849,
            6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        ServerThread server = new ServerThread(numbers);
        ProblemClientThread client1 = new ProblemClientThread();
        ProblemClientThread client2 = new ProblemClientThread();
        ProblemClientThread client3 = new ProblemClientThread();
        server.start();
        client1.start();
        client2.start();
        client3.start();

        try {
            client1.join();
            client2.join();
            client3.join();
            server.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
