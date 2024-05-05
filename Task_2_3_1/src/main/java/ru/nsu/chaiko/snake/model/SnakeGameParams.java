package ru.nsu.chaiko.snake.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Holds parameters and initialization methods for the Snake game.
 */
public class SnakeGameParams {
    /** Number of columns in the game field. */
    public static final int COLUMNS_COUNT = 20;
    /** Number of rows in the game field. */
    public static final int ROWS_COUNT = 20;
    /** Size of each cell in the game field. */
    public static final int CELL_SIZE = 30;
    /** Number of segments required to win the game. */
    public static final int SNAKE_SIZE_TO_WIN = 6;
    /** Number of food items initially on the game field. */
    public static final int FOOD_COUNT = 3;
    /** Indicates whether the game is over. */
    public static AtomicBoolean gameLost = new AtomicBoolean(false);
    /** Indicates whether the app is over. */
    public static AtomicBoolean appIsOver = new AtomicBoolean(false);

    /**
     * Initializes the game field with default values and settings.
     *
     * @param field The game field represented as a 2D array of GameField objects.
     * @param root The root group where the game field will be added.
     */
    static void initField(GameField[][] field, Group root) {
        for (int i = 0; i < SnakeGameParams.ROWS_COUNT; i++) {
            for (int j = 0; j < SnakeGameParams.COLUMNS_COUNT; j++) {
                field[i][j] = new GameField();

                field[i][j].setX(SnakeGameParams.CELL_SIZE * i);
                field[i][j].setY(SnakeGameParams.CELL_SIZE * j);
                field[i][j].setWidth(SnakeGameParams.CELL_SIZE);
                field[i][j].setHeight(SnakeGameParams.CELL_SIZE);
                field[i][j].setFill(Color.GREEN);
                field[i][j].setStroke(Color.BLACK);

                root.getChildren().add(field[i][j]);
            }
        }
    }
}
