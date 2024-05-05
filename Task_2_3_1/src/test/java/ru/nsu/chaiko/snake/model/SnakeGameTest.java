package ru.nsu.chaiko.snake.model;

import javafx.scene.Group;
import org.junit.jupiter.api.Test;

/**
 * Test class for SnakeGame.
 */
public class SnakeGameTest {
    /**
     * Test case for initializing the game field and generating food.
     */
    @Test
    void test1() {
        GameField[][] field = new GameField[20][20];
        SnakeGameParams.initField(field, new Group());
        Food.generateNewFood(field);
    }

    /**
     * Test case for generating and removing food.
     */
    @Test
    void test2() {
        GameField[][] field = new GameField[20][20];
        SnakeGameParams.initField(field, new Group());
        Food.generateTwo();
        Food.removeFood(new GameField());
    }

    /**
     * Test case for setting and getting snake position.
     */
    @Test
    void test3() {
        GameField snakeField = new GameField();
        int initialX = snakeField.getSnakeX();
        int initialY = snakeField.getSnakeY();
        snakeField.setSnakeX(1.0);
        snakeField.setSnakeY(2.0);
        assert snakeField.getSnakeX() == 1;
        assert snakeField.getSnakeY() == 2;
        snakeField.setSnakeX(initialX);
        snakeField.setSnakeY(initialY);
    }
}
