package ru.nsu.chaiko;

/**
 * exception for unsupported input.
 */
public class IncorrectInputFormatException extends Exception {
    public IncorrectInputFormatException(String error) {
        super(error);
    }
}
