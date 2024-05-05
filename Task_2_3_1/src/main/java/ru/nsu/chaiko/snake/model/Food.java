package ru.nsu.chaiko.snake.model;

import javafx.scene.paint.Color;

import java.util.HashSet;

/**
 * Represents food in the Snake game.
 */
public class Food {
    /** Stores the current food list. */
    static final HashSet<GameField> foodList = new HashSet<>();
    /** Stores the current x-coordinate of the food. */
    static int currentX;
    /** Stores the current y-coordinate of the food. */
    static int currentY;

    /**
     * Generates new food on the game field.
     *
     * @param field The game field represented as a 2D array of GameField objects.
     */
    public static void generateNewFood(GameField[][] field) {
        generateTwo();
        while (field[currentX][currentY].getFill() != Color.GREEN
                && !foodList.contains(field[currentX][currentY])) {
            generateTwo();
        }
        field[currentX][currentY].setFill(Color.CORAL);
        foodList.add(field[currentX][currentY]);
    }

    /**
     * Generates new random coordinates for food.
     */
    public static void generateTwo() {
        currentX = (int) (Math.random() * (SnakeGameParams.COLUMNS_COUNT - 1));
        currentY = (int) (Math.random() * (SnakeGameParams.ROWS_COUNT - 1));
    }

    /**
     * Removes food from the game field.
     *
     * @param food The food to be removed represented as a GameField object.
     */
    public static void removeFood(GameField food) {
        food.setFill(Color.GREEN);
        foodList.remove(food);
    }
}
