package ru.nsu.chaiko.snake.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a single field element in the Snake game.
 */
public class GameField extends Rectangle {
    private double snakeX, snakeY;

    /**
     * Constructs a GameField object with the specified coordinates.
     *
     * @param x The x-coordinate of the field.
     * @param y The y-coordinate of the field.
     */
    public GameField(double x, double y) {
        this.snakeX = x;
        this.snakeY = y;
        setFill(Color.BLUE);
        setWidth(SnakeGameParams.CELL_SIZE);
        setHeight(SnakeGameParams.CELL_SIZE);
        setX(x * SnakeGameParams.CELL_SIZE);
        setY(y * SnakeGameParams.CELL_SIZE);
    }

    /**
     * Constructs a GameField object with default settings.
     */
    public GameField() {
        this.setFill(Color.GREEN);
        setWidth(SnakeGameParams.CELL_SIZE);
        setHeight(SnakeGameParams.CELL_SIZE);
    }

    /**
     * Gets the x-coordinate of the field.
     *
     * @return The x-coordinate of the field.
     */
    public int getSnakeX() {
        return (int) snakeX;
    }

    /**
     * Sets the x-coordinate of the field and updates its position on the screen.
     *
     * @param snakeX The new x-coordinate of the field.
     */
    public void setSnakeX(double snakeX) {
        this.snakeX = snakeX;
        setX(snakeX * SnakeGameParams.CELL_SIZE);
        setFill(Color.BLUE);
    }

    /**
     * Gets the y-coordinate of the field.
     *
     * @return The y-coordinate of the field.
     */
    public int getSnakeY() {
        return (int) snakeY;
    }

    /**
     * Sets the y-coordinate of the field and updates its position on the screen.
     *
     * @param snakeY The new y-coordinate of the field.
     */
    public void setSnakeY(double snakeY) {
        this.snakeY = snakeY;
        setY(snakeY * SnakeGameParams.CELL_SIZE);
        setFill(Color.BLUE);
    }
}
