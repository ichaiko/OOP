package ru.nsu.chaiko.snake.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import ru.nsu.chaiko.snake.model.Main;
import ru.nsu.chaiko.snake.model.SnakeGameParams;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the buttons and their actions in the game.
 */
public class ButtonsController implements Initializable {

    /**
     * Starts the game when the start button is clicked.
     */
    @FXML
    void startAction() {
        Main.view.show("game", Main.root, SnakeGameParams.COLUMNS_COUNT * SnakeGameParams.CELL_SIZE,
                SnakeGameParams.ROWS_COUNT * SnakeGameParams.CELL_SIZE);
        Platform.runLater(() -> Main.root.requestFocus());
    }

    /**
     * Restarts the game when the restart button is clicked.
     */
    @FXML
    void restartGame() {
        Main.view.show("Another Try");
    }

    /**
     * Terminates the application when the terminate button is clicked.
     */
    @FXML
    void terminateApp() {
        Platform.exit();
        SnakeGameParams.appIsOver.set(true);
    }

    /**
     * Initializes the controller.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
