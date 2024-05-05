package ru.nsu.chaiko.snake.controller;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import ru.nsu.chaiko.snake.model.Food;
import ru.nsu.chaiko.snake.model.GameField;
import ru.nsu.chaiko.snake.model.SnakeGameParams;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controls the movement and behavior of the snake in the game.
 */
public class SnakeController {
    private final ArrayList<GameField> snake;
    private final Group root;
    private final GameField[][] field;

    /**
     * Constructs a SnakeController with the specified snake, root group, and game field.
     *
     * @param snake The snake represented as a list of GameField objects.
     * @param root  The root group where the snake is placed.
     * @param field The game field represented as a 2D array of GameField objects.
     */
    public SnakeController(ArrayList<GameField> snake, Group root, GameField[][] field) {
        this.snake = snake;
        this.root = root;
        this.field = field;
    }

    /**
     * Handles the movement of the snake based on user input.
     *
     * @param lastPressedKey   The reference to the last pressed key by the user.
     * @param lastPressedKeys  The queue of previously pressed keys.
     */
    public void movementHandler(AtomicReference<KeyCode> lastPressedKey,
                                ArrayDeque<AtomicReference<KeyCode>> lastPressedKeys,
                                AtomicReference<KeyCode> currentDirectionKey) {

        Thread movement = new Thread(() -> {
            while (!SnakeGameParams.appIsOver.get()) {
                try {
                    AtomicReference<KeyCode> key = null;
                    if (!lastPressedKeys.isEmpty()) {
                        key = lastPressedKeys.pollFirst();
                    }

                    moveSnake(Objects.requireNonNullElse(key, lastPressedKey), currentDirectionKey);

                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        movement.start();
    }

    private void moveSnake(AtomicReference<KeyCode> lastPressedKey, AtomicReference<KeyCode> currentDirectionKey) {
        var key = lastPressedKey.get();
        if (key == null) {
            return;
        }

        boolean foodEaten = false;

        GameField snakeHead = snake.get(0), current;
        GameField previous = new GameField(snakeHead.getSnakeX(), snakeHead.getSnakeY()), element;

        switch (key) {
            case D -> {
                currentDirectionKey.set(key);
                if (field[(snakeHead.getSnakeX() + 1) % SnakeGameParams.ROWS_COUNT]
                        [snakeHead.getSnakeY()].getFill() == Color.CORAL) {
                    element = new GameField((snakeHead.getSnakeX() + 1)
                            % SnakeGameParams.ROWS_COUNT,
                            snakeHead.getSnakeY());
                    snake.add(0, element);
                    foodEaten = true;
                    Platform.runLater(() -> root.getChildren().add(element));
                    Food.removeFood(field[(snakeHead.getSnakeX() + 1) % SnakeGameParams.ROWS_COUNT]
                            [snakeHead.getSnakeY()]);
                } else {
                    snakeHead.setSnakeX((snakeHead.getSnakeX() + 1)
                            % SnakeGameParams.ROWS_COUNT);
                }
            }
            case A -> {
                currentDirectionKey.set(key);
                int a = snakeHead.getSnakeX();
                if (a - 1 < 0) {
                    a = SnakeGameParams.ROWS_COUNT;
                }
                if (field[(a - 1) % SnakeGameParams.ROWS_COUNT]
                        [snakeHead.getSnakeY()].getFill() == Color.CORAL) {
                    element = new GameField((a - 1) % SnakeGameParams.ROWS_COUNT,
                            snakeHead.getSnakeY());
                    snake.add(0, element);
                    foodEaten = true;
                    Platform.runLater(() -> root.getChildren().add(element));
                    Food.removeFood(field[(a - 1) % SnakeGameParams.ROWS_COUNT]
                            [snakeHead.getSnakeY()]);
                } else {
                    snakeHead.setSnakeX((a - 1) % SnakeGameParams.ROWS_COUNT);
                }
            }
            case W -> {
                currentDirectionKey.set(key);
                var w = snakeHead.getSnakeY();
                if (w - 1 < 0) {
                    w = SnakeGameParams.COLUMNS_COUNT;
                }
                if (field[snakeHead.getSnakeX()][(w - 1)
                        % SnakeGameParams.COLUMNS_COUNT].getFill() == Color.CORAL) {
                    element = new GameField(snakeHead.getSnakeX(),
                            (w - 1) % SnakeGameParams.COLUMNS_COUNT);
                    snake.add(0, element);
                    foodEaten = true;
                    Platform.runLater(() -> root.getChildren().add(element));
                    Food.removeFood(field[snakeHead.getSnakeX()][(w - 1)
                            % SnakeGameParams.COLUMNS_COUNT]);
                } else {
                    snakeHead.setSnakeY((w - 1) % SnakeGameParams.COLUMNS_COUNT);
                }
            }
            case S -> {
                currentDirectionKey.set(key);
                if (field[snakeHead.getSnakeX()]
                        [(snakeHead.getSnakeY() + 1)
                        % SnakeGameParams.COLUMNS_COUNT].getFill() == Color.CORAL) {
                    element = new GameField(snakeHead.getSnakeX(),
                            (snakeHead.getSnakeY() + 1) % SnakeGameParams.COLUMNS_COUNT);
                    snake.add(0, element);
                    foodEaten = true;
                    Platform.runLater(() -> root.getChildren().add(element));
                    Food.removeFood(field[snakeHead.getSnakeX()]
                            [(snakeHead.getSnakeY() + 1)
                            % SnakeGameParams.COLUMNS_COUNT]);
                } else {
                    snakeHead.setSnakeY((snakeHead.getSnakeY() + 1)
                            % SnakeGameParams.COLUMNS_COUNT);
                }
            }
        }

        if (!foodEaten) {
            for (int i = 1; i < snake.size(); i++) {
                GameField elem = snake.get(i);
                if (elem.getSnakeX() == snakeHead.getSnakeX() && elem.getSnakeY() == snakeHead.getSnakeY()) {
                    SnakeGameParams.gameLost.set(true);
                }
            }

            for (int i = 1; i < snake.size(); i++) {
                current = snake.get(i);
                int x = previous.getSnakeX(), y = previous.getSnakeY();
                previous = new GameField(current.getSnakeX(), current.getSnakeY());
                current.setSnakeX(x);
                current.setSnakeY(y);
            }
        } else {
            Food.generateNewFood(field);
        }
    }
}
