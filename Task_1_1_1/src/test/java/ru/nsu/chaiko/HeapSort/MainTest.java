package ru.nsu.chaiko.HeapSort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Random;
import java.util.Arrays;

class MainTest {
    @Test
    void test() {
        sample(new int[] {5, 4, 3, 2, 1}, new int[] {1, 2, 3, 4, 5});
        sample(new int[] {8, 19, 2, 14, 2, 22, -2204, 523, 0, -117}, new int[] {-2204, -117, 0, 2, 2, 8, 14, 19, 22, 523});

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

}