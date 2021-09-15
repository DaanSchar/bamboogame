package com.maastricht.university.frontend;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenu extends Application {

    //public Image humanPNG = new Image()
    @Override
    public void start(Stage stage) {
        StackPane r = new StackPane();
        Scene scene = new Scene(r,640, 480);
        stage.setScene(scene);

        Button human = new Button("Human v Human");
        r.getChildren().add(human);
        r.setAlignment(human, Pos.TOP_CENTER);

        Button computer = new Button("Human v Computer");
        r.getChildren().add(computer);
        r.setAlignment(computer, Pos.CENTER);

        Button exit = new Button("Exit Game");
        r.getChildren().add(exit);
        r.setAlignment(exit, Pos.BOTTOM_CENTER);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
