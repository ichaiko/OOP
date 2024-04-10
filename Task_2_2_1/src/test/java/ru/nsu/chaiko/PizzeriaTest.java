package ru.nsu.chaiko;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class PizzeriaTest {
    @Test
    void test1() throws FileNotFoundException, InterruptedException {
        Main.main(new String[]{"jsonTests/pizzeriaParameters1.json", "remainedOrders1"});
    }

}
