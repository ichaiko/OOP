package ru.nsu.chaiko.snake;

import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Holds parameters and initialization methods for the Snake game.
 */
public class SnakeGameParams {
    /** Number of columns in the game field. */
    static final int columnsCount = 20;
    /** Number of rows in the game field. */
    static final int rowsCount = 20;
    /** Size of each cell in the game field. */
    static final int cellSize = 30;
    /** Number of segments required to win the game. */
    static final int sizeToWin = 7;
    /** Number of food items initially on the game field. */
    static final int foodCount = 3;
    /** Indicates whether the game is over. */
    static AtomicBoolean gameIsOver = new AtomicBoolean(false);

    /**
     * Initializes the game field with default values and settings.
     *
     * @param field The game field represented as a 2D array of GameField objects.
     * @param root The root group where the game field will be added.
     */
    static void initField(GameField[][] field, Group root) {
        for (int i = 0; i < SnakeGameParams.rowsCount; i++) {
            for (int j = 0; j < SnakeGameParams.columnsCount; j++) {
                field[i][j] = new GameField();

                field[i][j].setX(SnakeGameParams.cellSize * i);
                field[i][j].setY(SnakeGameParams.cellSize * j);
                field[i][j].setWidth(SnakeGameParams.cellSize);
                field[i][j].setHeight(SnakeGameParams.cellSize);
                field[i][j].setFill(Color.GREEN);
                field[i][j].setStroke(Color.BLACK);

                root.getChildren().add(field[i][j]);
            }
        }
    }
}
