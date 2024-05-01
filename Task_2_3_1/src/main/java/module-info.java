module ru.nsu.chaiko.snake {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens ru.nsu.chaiko.snake to javafx.fxml;
    exports ru.nsu.chaiko.snake;
}
