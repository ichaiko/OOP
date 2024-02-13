package ru.nsu.chaiko;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SolutionsTests {
    @Test
    void test1() throws InterruptedException {
        var obj = new SolutionsForPrimeChecking(new int[] {6, 8, 7, 13, 5, 9, 4});
        Assertions.assertTrue(obj.sequentialSolution());
        Assertions.assertTrue(obj.multiThreadSolution(4));
        Assertions.assertTrue(obj.parallelStreamSolution());
    }

    @Test
    void test2() throws InterruptedException {
        var obj = new SolutionsForPrimeChecking(new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053});

        Assertions.assertFalse(obj.sequentialSolution());
        Assertions.assertFalse(obj.multiThreadSolution(6));
        Assertions.assertFalse(obj.parallelStreamSolution());
    }

    @Test
    void test3() throws InterruptedException {
        var obj = new SolutionsForPrimeChecking(new int[] {0, 1});

        Assertions.assertFalse(obj.sequentialSolution());
        Assertions.assertFalse(obj.multiThreadSolution(6));
        Assertions.assertFalse(obj.parallelStreamSolution());
    }

    @Test
    void test4() throws InterruptedException {
        var res1 = PrimeNumberCheck.isNumberNotPrime(5);
        var res2 = PrimeNumberCheck.isNumberNotPrime(8);
        var res3 = PrimeNumberCheck.isNumberNotPrime(1);

        Assertions.assertFalse(res1);
        Assertions.assertTrue(res2);
        Assertions.assertFalse(res3);
    }

    @Test
    void test5() {
        var thread = new ThreadForPrimes(new int[] {5, 6, 7, 8});

        thread.start();
        Assertions.assertFalse(PrimeNumberCheck.flag.get());
    }
}
