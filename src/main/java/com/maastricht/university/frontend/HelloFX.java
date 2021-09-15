package com.maastricht.university.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        Pane playground = new Pane();
        Tile a = new Tile(200, 300, 100, 0);
        Tile b = new Tile(100, 100, 200, 1);
        playground.getChildren().add(a.getButton());
        playground.getChildren().add(b.getButton());
        Scene scene = new Scene(playground, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}