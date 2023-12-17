package com.example.pathfinding;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("canvas.fxml"));
        Pane root = fxmlLoader.load();
        WindowController controller = fxmlLoader.getController();

        Scene scene = new Scene(root, 700, 400);
        controller.setRoot(root);
        controller.makeBoard();

        stage.setTitle("Pathfinding");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}