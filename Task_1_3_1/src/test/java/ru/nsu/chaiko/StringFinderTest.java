package ru.nsu.chaiko;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringFinderTest {
    @Test
    void startTest() throws IOException {
        StringFinder actual = new StringFinder("бра");
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 8));
        assertEquals(expected, actual.find("src/test/java/ru/nsu/chaiko/input1.txt"));
    }

    @Test
    void test2() throws IOException{
        StringFinder actual = new StringFinder("asp");
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(8, 11));
        assertEquals(expected, actual.find("src/test/java/ru/nsu/chaiko/input2.txt"));
    }

    @Test
    void test3() throws IOException {
        StringFinder actual = new StringFinder("as");
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(0, 12, 7));
        assertEquals(expected, actual.find("src/test/java/ru/nsu/chaiko/input3.txt"));
    }

    @Test
    void test4() throws IOException {
        StringFinder actual = new StringFinder("");
        ArrayList<Integer> expected = new ArrayList<>();
        assertEquals(expected, actual.find("src/test/java/ru/nsu/chaiko/input4.txt"));
    }

    /**
     * big file test.
     */
    @Test
    void test5() throws IOException {
        String path = "src/test/java/ru/nsu/chaiko/input5.txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for(int i = 0; i < 300000; i++) {
                for(int j = 0; j < 800; j++) {
                    writer.write("abccdedQaF");
                }

                writer.newLine();
            }

            writer.write("asbcdas");

            for(int i = 0; i < 700000; i++) {
                for(int j = 0; j < 800; j++) {
                    writer.write("abccdedQaF");
                }

                writer.newLine();
            }
        }

        StringFinder actual = new StringFinder("as");
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(0, 5));
        assertEquals(expected, actual.find("src/test/java/ru/nsu/chaiko/input5.txt"));
    }
}
