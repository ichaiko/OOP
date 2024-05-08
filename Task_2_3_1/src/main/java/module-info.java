module ru.nsu.chaiko.snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.chaiko.snake.model to javafx.fxml;
    exports ru.nsu.chaiko.snake.model;
    opens ru.nsu.chaiko.snake.view to javafx.fxml;
    exports ru.nsu.chaiko.snake.view;
    opens ru.nsu.chaiko.snake.controller to javafx.fxml;
    exports ru.nsu.chaiko.snake.controller;
}
