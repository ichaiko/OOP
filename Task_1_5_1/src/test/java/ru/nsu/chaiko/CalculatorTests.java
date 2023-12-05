package ru.nsu.chaiko;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * calculator's tests.
 */
public class CalculatorTests {
    @Test
    void test1() throws NoInputException, NoNumberException {
        Calculator calc = new Calculator(true, "sin + - 1 2 1");
        Assertions.assertEquals(0, calc.calculateAnswer());
    }

    @Test
    void test2() throws NoInputException, NoNumberException {
        Calculator calc = new Calculator(true, "+ 4 - 5 2");
        Assertions.assertEquals(7.0, calc.calculateAnswer());
    }

    @Test
    void test3() throws NoInputException, NoNumberException {
        Calculator calc = new Calculator(true, "pow + 2 3 3");
        Assertions.assertEquals(125.0, calc.calculateAnswer());
    }

    @Test
    void test4() throws NoInputException {
        Assertions.assertThrows(NoInputException.class, () -> {
            Calculator calc = new Calculator(true);
        });
    }
}
