package com.maastricht.university.frontend;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(Factory.getMenuScene());
        stage.show();
    }
}
