package com.maastricht.university.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Pane playground = new Pane();
        //Commented this out for now
        //Tile a = new Tile(200, 300, 100, 0);
        //Tile b = new Tile(100, 100, 200, 1);
        //playground.getChildren().add(a.getButton());
        //playground.getChildren().add(b.getButton());

        TileMap tilemap = new TileMap(0,0,30);
        int dim = tilemap.getDimension();
        Tile[][] map = tilemap.getTileMap();

        for(int i = 0; i<dim;i++) {
            for(int j = 0; j<dim;j++) {
                if(map[i][j] != null)
                    playground.getChildren().add(map[i][j].getButton());
            }
        }
        Scene scene = new Scene(playground, 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}