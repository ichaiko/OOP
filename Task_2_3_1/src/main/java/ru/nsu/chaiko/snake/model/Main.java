package ru.nsu.chaiko.snake.model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ru.nsu.chaiko.snake.controller.SnakeController;
import ru.nsu.chaiko.snake.view.SnakeGameView;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The Main class responsible for initializing and running the Snake game.
 */
public class Main extends Application {
    public static SnakeGameView view;
    public static Group root;

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
        SnakeGameView loseView = new SnakeGameView(stage);
        loseView.loadFromFxml("loseScreen.fxml");

        SnakeGameView winView = new SnakeGameView(stage);
        winView.loadFromFxml("wonScreen.fxml");

        SnakeGameView startView = new SnakeGameView(stage);
        startView.loadFromFxml("startScreen.fxml");
        startView.show("Start Screen");

        Main.view = startView;

        GameField[][] field = new GameField[SnakeGameParams.ROWS_COUNT][SnakeGameParams.COLUMNS_COUNT];
        Group root = new Group();
        GameField snakeHead = new GameField(SnakeGameParams.COLUMNS_COUNT - 1, SnakeGameParams.ROWS_COUNT - 1);
        ArrayList<GameField> snake = new ArrayList<>();
        AtomicReference<KeyCode> lastPressedKey = new AtomicReference<>();
        ArrayDeque<AtomicReference<KeyCode>> lastPressedKeys = new ArrayDeque<>();

        SnakeGameParams.initField(field, root);

        for (int i = 0; i < SnakeGameParams.FOOD_COUNT; i++) {
            Food.generateNewFood(field);
        }

        snake.add(snakeHead);

        root.getChildren().add(snakeHead);
        Main.root = root;
        AtomicReference<KeyCode> currentDirectionKey = new AtomicReference<>();

        root.requestFocus();
        root.setOnKeyPressed(event -> {
            KeyCode pressedKey = event.getCode();

            switch (pressedKey) {
                case D -> {
                    if (currentDirectionKey.get() != KeyCode.A) {
                        lastPressedKey.set(event.getCode());
                    }
                }
                case A -> {
                    if (currentDirectionKey.get() != KeyCode.D) {
                        lastPressedKey.set(event.getCode());
                    }
                }
                case W -> {
                    if (currentDirectionKey.get() != KeyCode.S) {
                        lastPressedKey.set(event.getCode());
                    }
                }
                case S -> {
                    if (currentDirectionKey.get() != KeyCode.W) {
                        lastPressedKey.set(event.getCode());
                    }
                }
            }

            lastPressedKeys.add(lastPressedKey);
        });

        SnakeController snakeController = new SnakeController(snake, root, field);
        snakeController.movementHandler(lastPressedKey, lastPressedKeys, currentDirectionKey);

        Thread endGame = new Thread(() -> {
            while (!SnakeGameParams.appIsOver.get()) {

                if (SnakeGameParams.gameLost.get()) {
                    Platform.runLater(() -> loseView.show("you lose"));
                    SnakeGameParams.gameLost.set(false);
                    Platform.runLater(() -> {
                        for (int i = snake.size() - 1; i > 0; i--) {
                            root.getChildren().remove(snake.get(i));
                            snake.remove(i);
                        }
                        lastPressedKey.set(null);
                    });
                }

                if (SnakeGameParams.SNAKE_SIZE_TO_WIN == snake.size()) {
                    Platform.runLater(() -> winView.show("you won"));
                    Platform.runLater(() -> {
                        for (int i = snake.size() - 1; i > 0; i--) {
                            root.getChildren().remove(snake.get(i));
                            snake.remove(i);
                        }
                        lastPressedKey.set(null);
                    });
                }
            }
        });

        endGame.start();
    }
}
