package ru.nsu.chaiko;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
public class PizzeriaTest {

    /**
     * test.
     *
     * @throws FileNotFoundException
     *
     * @throws InterruptedException
     */
    @Test
    void test1() throws FileNotFoundException, InterruptedException {
        Main.main(new String[]{"jsonTests/pizzeriaParameters1.json", "remainedOrders1"});
    }

}
