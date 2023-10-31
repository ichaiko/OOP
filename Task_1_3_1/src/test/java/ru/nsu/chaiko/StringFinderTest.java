package ru.nsu.chaiko;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class for test.
 */
public class StringFinderTest {
    @Test
    void startTest() throws IOException {
        StringFinder actual = new StringFinder("бра");
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 8));
        assertEquals(expected, actual.find("src/test/java/ru/nsu/chaiko/input1.txt", 1000));
    }

    @Test
    void test2() throws IOException {
        StringFinder actual = new StringFinder("asp");
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(8, 11));
        assertEquals(expected, actual.find("src/test/java/ru/nsu/chaiko/input2.txt", 1000));
    }

    @Test
    void test4() throws IOException {
        StringFinder actual = new StringFinder("");
        ArrayList<Integer> expected = new ArrayList<>();
        assertEquals(expected, actual.find("src/test/java/ru/nsu/chaiko/input4.txt", 1000));
    }

    /**
     * big file test.
     */
    @Test
    void test5() throws IOException {
        String path = "src/test/java/ru/nsu/chaiko/input5.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < 20000; i++) {
                writer.write("abccdedQaF");
            }
            writer.write("fQb");
            for (int i = 0; i < 20000; i++) {
                writer.write("abccdedQaF");
            }
            writer.write("fQb");
        }

        StringFinder actual = new StringFinder("fQb");
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(200000, 400003));
        assertEquals(expected, actual.find("src/test/java/ru/nsu/chaiko/input5.txt", 1000_000));
    }
}
