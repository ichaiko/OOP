package ru.nsu.chaiko;

import java.io.*;
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
    ArrayList<Integer> find(String filePath) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();

        try(BufferedReader bufReader = new BufferedReader(new FileReader(filePath))) {
            String separateLine;

            while ((separateLine = bufReader.readLine()) != null) {

                for(int i = 0; i < separateLine.length(); i++) {
                    for(int j = 0; j < targetString.length(); j++) {
                        if(separateLine.charAt(i+j) == targetString.charAt(j) && i + j < separateLine.length()) {
                            continue;
                        } else {
                            flag = false;
                            break;
                        }
                    }

                    if(flag) {
                        list.add(i);
                    }
                    flag = true;
                }

            }
        }

        return list;
    }
}
