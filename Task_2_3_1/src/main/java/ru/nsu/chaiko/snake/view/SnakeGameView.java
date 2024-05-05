package ru.nsu.chaiko.snake.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the view for the Snake game.
 */
public class SnakeGameView {
    private final Stage stage;
    private Scene scene;

    /**
     * Constructs a SnakeGameView with the specified stage.
     *
     * @param stage The stage to associate with the view.
     */
    public SnakeGameView(Stage stage) {
        this.stage = stage;
    }

    /**
     * Loads the view from an FXML file.
     *
     * @param sourceName The name of the FXML file to load.
     * @throws IOException If an error occurs during loading.
     */
    public void loadFromFxml(String sourceName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/nsu/chaiko/snake/view/" + sourceName));
        Parent parent = loader.load();
        this.scene = new Scene(parent);
    }

    /**
     * Shows the view with the specified title.
     *
     * @param title The title of the view.
     */
    public void show(String title) {
        this.stage.setTitle(title);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    /**
     * Shows the view with the specified title, root group, width, and height.
     *
     * @param title  The title of the view.
     * @param root   The root group of the view.
     * @param width  The width of the view.
     * @param height The height of the view.
     */
    public void show(String title, Group root, double width, double height) {
        this.scene = new Scene(root, width, height);
        show(title);
    }
}
