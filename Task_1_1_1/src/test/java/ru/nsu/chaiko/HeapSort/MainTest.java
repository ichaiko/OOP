package ru.nsu.chaiko.HeapSort;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Random;

class MainTest {
    @Test
    void test() {
        int[] testArr = {5, 4, 3, 2, 1}, expected = {1, 2, 3, 4, 5};
        sample(testArr, expected);
        testArr = new int[] {8, 19, 2, 14, 2, 22, -2204, 0, -117};
        expected = new int[] {-2204, -117, 0, 2, 2, 8, 14, 19, 22};
        sample(testArr, expected);
    }

    void sample(int[] inputArr, int[] expectedArr) {
        HeapSort heap = new HeapSort(inputArr);
        int[] myArr = heap.heapsort();
        Assertions.assertArrayEquals(expectedArr, myArr);
    }

    @Test
    void heapSortLongTest() {
        int length = 100000;
        int[] inputArr = new int[length];
        int[] expectedArr = new int[length];
        int maxInt = Integer.MAX_VALUE;
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            inputArr[i] = random.nextInt(maxInt);
            expectedArr[i] = inputArr[i];
        }

        HeapSort heap = new HeapSort(inputArr);
        Arrays.sort(expectedArr);

        int[] myArr = heap.heapsort();
        Assertions.assertArrayEquals(expectedArr, myArr);
    }

    @Test
    void heapSortEmptyArr() {
        HeapSort heap = new HeapSort(new int[]{});
        int[] myArr = heap.heapsort();
        int[] expectedArr = {};
        Assertions.assertArrayEquals(expectedArr, myArr);
    }

}