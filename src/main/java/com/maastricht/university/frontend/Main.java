package com.maastricht.university.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        TileMap tilemap = new TileMap(0,0,30);
        Pane playground = tilemap.getTileMapPane();
        System.out.println(getClass().getResource("sample.css"));

        Scene scene = new Scene(playground, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}