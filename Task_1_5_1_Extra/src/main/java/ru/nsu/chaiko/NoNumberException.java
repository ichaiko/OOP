package ru.nsu.chaiko;

/**
 * exception for missed number.
 */
public class NoNumberException extends Exception {
    public NoNumberException(String error) {
        super(error);
    }
}
