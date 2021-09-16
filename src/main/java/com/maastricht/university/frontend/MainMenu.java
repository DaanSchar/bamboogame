package com.maastricht.university.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.awt.*;

public class MainMenu extends Application {

    public Image humanPNG = new Image("resources/button_human.png");
    public Image pcPNG = new Image("resources/button_computer.png");
    public Image exitPNG = new Image("resources/button_exit.png");
    public Image rulesPNG = new Image("resources/button_rules.png");
    public Image bambooText = new Image("resources/bambooText.png");

    public ImageView humanView = new ImageView(humanPNG);
    public ImageView pcView = new ImageView(pcPNG);
    public ImageView exitView = new ImageView(exitPNG);
    public ImageView rulesView = new ImageView(rulesPNG);
    public ImageView title = new ImageView(bambooText);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    public Image bambooBcg = new Image("resources/bamboo.jpg",width,height,false,true);

    @Override
    public void start(Stage stage) {
        Pane r = new Pane();
        Scene scene = new Scene(r,width, height);

        //set background picture
        BackgroundImage bImg = new BackgroundImage(bambooBcg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        r.setBackground(bGround);

        stage.setScene(scene);

        //add title to scene
        title.setLayoutX(width/2);
        title.setLayoutY(50);
        title.setFitWidth(270);
        title.setFitHeight(100);
        r.getChildren().add(title);

        //create buttons and add the design
        HoverableButton human = new HoverableButton(width/2,height/2,20,20);
        humanView.setFitHeight(100);
        humanView.setFitWidth(270);
        human.setGraphic(humanView);

        HoverableButton computer = new HoverableButton(width/2,height/3,20,20);
        pcView.setFitHeight(100);
        pcView.setFitWidth(270);
        computer.setGraphic(pcView);

        HoverableButton exit = new HoverableButton(200,height-height/4,20,20);
        exitView.setFitHeight(90);
        exitView.setFitWidth(175);
        exit.setGraphic(exitView);

        HoverableButton rules = new HoverableButton(width - width/6, height/6,20,20);
        rulesView.setFitHeight(90);
        rulesView.setFitWidth(175);
        rules.setGraphic(rulesView);


        //add to scene
        r.getChildren().add(human);
        r.getChildren().add(computer);
        r.getChildren().add(exit);
        r.getChildren().add(rules);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
