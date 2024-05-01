package ru.nsu.chaiko.snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The Main class responsible for initializing and running the Snake game.
 */
public class Main extends Application {

    /**
     * Main method to launch the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If an error occurs during loading of FXML files.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader wonLoader = new FXMLLoader(getClass().getResource("wonScreen.fxml"));
        Parent wonP = wonLoader.load();
        Scene wonScene = new Scene(wonP);

        FXMLLoader loseLoader = new FXMLLoader(getClass().getResource("loseScreen.fxml"));
        Parent loseP = loseLoader.load();
        Scene loseScene = new Scene(loseP);

        GameField[][] field = new GameField[SnakeGameParams.rowsCount][SnakeGameParams.columnsCount];
        Group root = new Group();
        GameField snakeHead = new GameField(SnakeGameParams.columnsCount - 1, SnakeGameParams.rowsCount - 1);
        ArrayList<GameField> snake = new ArrayList<>();
        AtomicReference<KeyCode> lastPressedKey = new AtomicReference<>();
        ArrayDeque<AtomicReference<KeyCode>> lastPressedKeys = new ArrayDeque<>();

        SnakeGameParams.initField(field, root);

        for (int i = 0; i < SnakeGameParams.foodCount; i++) {
            Food.generateNewFood(field);
        }

        snake.add(snakeHead);

        root.getChildren().add(snakeHead);

        Scene scene = new Scene(root,
                SnakeGameParams.columnsCount * SnakeGameParams.cellSize,
                SnakeGameParams.rowsCount * SnakeGameParams.cellSize);
        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.show();

        Thread movement = new Thread(() -> {
            while (true) {
                try {
                    if (SnakeGameParams.gameIsOver.get()) {
                        Platform.runLater(() -> stage.setTitle("lose screen"));
                        Platform.runLater(() -> stage.setScene(loseScene));
                        Platform.runLater(stage::show);
                        return;
                    }

                    if (snake.size() == SnakeGameParams.sizeToWin) {
                        Platform.runLater(() -> stage.setTitle("won screen"));
                        Platform.runLater(() -> stage.setScene(wonScene));
                        Platform.runLater(stage::show);
                        return;
                    }

                    AtomicReference<KeyCode> key = null;
                    if (!lastPressedKeys.isEmpty()) {
                        key = lastPressedKeys.pollFirst();
                    }

                    moveSnake(root, field, snake, Objects.requireNonNullElse(key, lastPressedKey));

                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        movement.start();

        root.requestFocus();
        root.setOnKeyPressed(event -> {
            lastPressedKey.set(event.getCode());
            lastPressedKeys.add(lastPressedKey);
        });
    }

    /**
     * Moves the snake according to the pressed key.
     *
     * @param root           The root group of the scene.
     * @param field          The game field.
     * @param snake          The snake body.
     * @param lastPressedKey The reference to the last pressed key.
     */
    private void moveSnake(Group root, GameField[][] field, ArrayList<GameField> snake,
                           AtomicReference<KeyCode> lastPressedKey) {
        var key = lastPressedKey.get();
        if (key == null) {
            return;
        }

        boolean foodEaten = false;

        GameField snakeHead = snake.get(0), current;
        GameField previous = new GameField(snakeHead.getSnakeX(), snakeHead.getSnakeY()), element;

        switch (key) {
            case D -> {
                if (field[(snakeHead.getSnakeX() + 1) % SnakeGameParams.rowsCount]
                        [snakeHead.getSnakeY()].getFill() == Color.CORAL) {
                    element = new GameField((snakeHead.getSnakeX() + 1)
                            % SnakeGameParams.rowsCount,
                            snakeHead.getSnakeY());
                    snake.add(0, element);
                    foodEaten = true;
                    Platform.runLater(() -> root.getChildren().add(element));
                    Food.removeFood(field[(snakeHead.getSnakeX() + 1) % SnakeGameParams.rowsCount]
                            [snakeHead.getSnakeY()]);
                } else {
                    snakeHead.setSnakeX((snakeHead.getSnakeX() + 1)
                            % SnakeGameParams.rowsCount);
                }
            }
            case A -> {
                int a = snakeHead.getSnakeX();
                if (a - 1 < 0) {
                    a = SnakeGameParams.rowsCount;
                }
                if (field[(a - 1) % SnakeGameParams.rowsCount]
                        [snakeHead.getSnakeY()].getFill() == Color.CORAL) {
                    element = new GameField((a - 1) % SnakeGameParams.rowsCount,
                            snakeHead.getSnakeY());
                    snake.add(0, element);
                    foodEaten = true;
                    Platform.runLater(() -> root.getChildren().add(element));
                    Food.removeFood(field[(a - 1) % SnakeGameParams.rowsCount]
                            [snakeHead.getSnakeY()]);
                } else {
                    snakeHead.setSnakeX((a - 1) % SnakeGameParams.rowsCount);
                }
            }
            case W -> {
                var w = snakeHead.getSnakeY();
                if (w - 1 < 0) {
                    w = SnakeGameParams.columnsCount;
                }
                if (field[snakeHead.getSnakeX()][(w - 1)
                        % SnakeGameParams.columnsCount].getFill() == Color.CORAL) {
                    element = new GameField(snakeHead.getSnakeX(),
                            (w - 1) % SnakeGameParams.columnsCount);
                    snake.add(0, element);
                    foodEaten = true;
                    Platform.runLater(() -> root.getChildren().add(element));
                    Food.removeFood(field[snakeHead.getSnakeX()][(w - 1)
                            % SnakeGameParams.columnsCount]);
                } else {
                    snakeHead.setSnakeY((w - 1) % SnakeGameParams.columnsCount);
                }
            }
            case S -> {
                if (field[snakeHead.getSnakeX()]
                        [(snakeHead.getSnakeY() + 1)
                        % SnakeGameParams.columnsCount].getFill() == Color.CORAL) {
                    element = new GameField(snakeHead.getSnakeX(),
                            (snakeHead.getSnakeY() + 1) % SnakeGameParams.columnsCount);
                    snake.add(0, element);
                    foodEaten = true;
                    Platform.runLater(() -> root.getChildren().add(element));
                    Food.removeFood(field[snakeHead.getSnakeX()]
                            [(snakeHead.getSnakeY() + 1)
                            % SnakeGameParams.columnsCount]);
                } else {
                    snakeHead.setSnakeY((snakeHead.getSnakeY() + 1)
                            % SnakeGameParams.columnsCount);
                }
            }
        }

        if (!foodEaten) {
            for (int i = 1; i < snake.size(); i++) {
                GameField elem = snake.get(i);
                if (elem.getSnakeX() == snakeHead.getSnakeX() && elem.getSnakeY() == snakeHead.getSnakeY()) {
                    SnakeGameParams.gameIsOver.set(true);
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
