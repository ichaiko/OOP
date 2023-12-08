package ru.nsu.chaiko;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * calculator's tests.
 */
public class CalculatorTests {
    @Test
    void test1() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "sin + - 1 2 1");
        Assertions.assertEquals("(0.0,0.0)", calc.calculateAnswer());
    }

    @Test
    void test2() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "+ 4 - 5 2");
        Assertions.assertEquals("(7.0,0.0)", calc.calculateAnswer());
    }

    @Test
    void test3() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "pow + 2 3 3");
        Assertions.assertEquals("(125.0,0.0)", calc.calculateAnswer());
    }

    @Test
    void test4() throws NoInputException {
        Assertions.assertThrows(NoInputException.class, () -> {
            Calculator calc = new Calculator(true);
        });
    }

    @Test
    void test5() throws NoInputException, NoNumberException {
        Assertions.assertThrows(NoNumberException.class, () -> {
            Calculator calc = new Calculator(true, "log");
            calc.calculateAnswer();
        });
    }

    @Test
    void test6() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "sqrt 16");
        Assertions.assertEquals("(4.0,0.0)", calc.calculateAnswer());
    }

    @Test
    void test7() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "sqrt pow - 7 -7 2");
        Assertions.assertEquals("(14.0,0.0)", calc.calculateAnswer());
    }

    @Test
    void test8() throws NoInputException, NoNumberException {
        Calculator calc = new Calculator(false);
    }

    @Test
    void test9() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "cos * / 5 5 0");
        Assertions.assertEquals("(1.0,0.0)", calc.calculateAnswer());
    }

    @Test
    void test10() {
        Assertions.assertThrows(IncorrectInputFormatException.class, () -> {
            Calculator calc = new Calculator(true, "sin (1.0,2.0)");
            calc.calculateAnswer();
        });
    }

    @Test
    void test11() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "sin 90`");
        Assertions.assertEquals("(1.0,0.0)", calc.calculateAnswer());
    }

    @Test
    void test12() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "+ sin 90` (2,3)");
        Assertions.assertEquals("(3.0,3.0)", calc.calculateAnswer());
    }

    @Test
    void test13() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "cos + sin 0` 180`");
        Assertions.assertEquals("(-1.0,0.0)", calc.calculateAnswer());
    }

    @Test
    void test14() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "* (1.0,2.5) (3.6,4.1)");
        Assertions.assertEquals("(-6.65,13.1)", calc.calculateAnswer());
    }

    @Test
    void test15() throws NoInputException, NoNumberException, IncorrectInputFormatException {
        Calculator calc = new Calculator(true, "+ (2.5,3.0) * (1.0,2.5) (3.6,4.1)");
        Assertions.assertEquals("(-4.15,16.1)", calc.calculateAnswer());
    }
}
