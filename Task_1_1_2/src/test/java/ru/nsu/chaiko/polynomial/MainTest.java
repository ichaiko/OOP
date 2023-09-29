package ru.nsu.chaiko.polynomial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void sampleTest() throws Exception {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});

        String polynomialActual = p1.plus(p2.differentiate(1)).toString();
        String polynomialExpected = "7x^3 + 6x^2 + 19x + 6";

        int valueActual = p1.multiply(p2).evaluate(2);
        int valueExpected = 3510;

        Assertions.assertEquals(polynomialExpected, polynomialActual);
        Assertions.assertEquals(valueExpected, valueActual);
    }

    @Test
    void test1() throws Exception {
        Polynomial p1 = new Polynomial(new int[] {0, 5, 2, -3, 0, 7, 11});
        Polynomial p2 = new Polynomial(new int[] {-7, 0, 4});
        Polynomial p3 = new Polynomial(new int[] {-7, 0, 4});

        String thirdDerivativeActual = p1.differentiate(3).toString();
        String thirdDerivativeExpected = "1320x^3 + 420x^2 - 18";

        String subtractionExpected = "-11x^6 - 7x^5 + 3x^3 + 2x^2 - 5x - 7";
        String subtractionActual = p2.subtraction(p1).toString();

        Assertions.assertEquals(thirdDerivativeExpected, thirdDerivativeActual);
        Assertions.assertEquals(subtractionExpected, subtractionActual);
        Assertions.assertFalse(p1.equals(p2));
        Assertions.assertTrue(p2.equals(p3));
    }

    @Test
    void test2() throws Exception {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            Polynomial p1 = new Polynomial(new int[] {});
        });

        Polynomial p2 = new Polynomial(new int[] {-1});
        Assertions.assertEquals("-1", p2.toString());
        Assertions.assertEquals(" ", p2.differentiate(1).toString());

    }
}
