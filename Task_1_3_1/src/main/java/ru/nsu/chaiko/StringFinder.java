package ru.nsu.chaiko;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * main class of the task.
 */
public class StringFinder {
    private final String targetString;
    private boolean flag;

    /**
     * class`es constructor.
     */
    public StringFinder(String targetString) {
        this.targetString = targetString;
        flag = true;
    }

    /**
     * function to find substring.
     */
    ArrayList<Integer> find(String filePath, int limit) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        int bufferSize = limit;

        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(filePath), bufferSize)) {
            byte[] buffer = new byte[bufferSize];
            int bytesRead;

            while ((bytesRead = stream.read(buffer)) != -1) {
                /**
                 * converting array of bytes to String.
                 */
                String partOfFile = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);

                for (int i = 0; i < partOfFile.length(); i++) {
                    for (int j = 0; j < targetString.length(); j++) {
                        if (partOfFile.charAt(i + j) != targetString.charAt(j) && i + j < partOfFile.length()) {
                            flag = false;
                            break;
                        }
                    }

                    if (flag) {
                        list.add(i);
                    }
                    flag = true;
                }
            }
        }
        return list;
    }
}
