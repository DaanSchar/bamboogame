package com.maastricht.university.frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainMenu extends Application {

    public ImageView humanView = new ImageView(new Image("/images/button_human.png"));
    public ImageView pcView = new ImageView(new Image("/images/button_computer.png"));
    public ImageView exitView = new ImageView(new Image("/images/button_exit.png"));
    public ImageView rulesView = new ImageView(new Image("/images/button_rules.png"));
    public ImageView title = new ImageView(new Image("/images/bambooText.png"));
    public ImageView tutorialView = new ImageView(new Image("/images/tutorial.gif"));

    double width = 800;
    double height = 500;
    public Image bambooBcg = new Image("/images/bamboo.jpeg", width, height, false, true);

    @Override
    public void start(Stage stage) {
        BorderPane r = new BorderPane();
        Scene scene = new Scene(r, width, height);

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
        title.setLayoutX(width / 6);
        title.setLayoutY(-height/6);
        title.setFitWidth(width/1.5);
        title.setFitHeight(width/2);
        r.getChildren().add(title);

        //create buttons and add the design
        HoverableButton human = new HoverableButton(width/2,height/2.5 , 20, 20);
        humanView.setFitHeight(75);
        humanView.setFitWidth(170);
        human.setGraphic(humanView);

        //TODO: make this button go to the game
        human.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("This goes into the game :)");
            }
        });

        HoverableButton computer = new HoverableButton(width / 2, height - height/3, 20, 20);
        pcView.setFitHeight(75);
        pcView.setFitWidth(170);
        computer.setGraphic(pcView);

        //TODO: make this button go to the game (phase 2)
        computer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("This goes into the game :)");
            }
        });

        computer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("This goes into the game :)");
            }
        });

        HoverableButton exit = new HoverableButton(50, height - height/16, 20, 20);
        exitView.setFitHeight(40);
        exitView.setFitWidth(80);
        exit.setGraphic(exitView);

        //if the exit button is clicked, it will close the program
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                System.exit(0);
            }
        });

        HoverableButton rules = new HoverableButton(width - width / 16, height / 16, 20, 20);
        rulesView.setFitHeight(40);
        rulesView.setFitWidth(80);
        rules.setGraphic(rulesView);

        rules.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage rulesWindow = new Stage();
                Pane r_2 = new Pane();
                Scene sc = new Scene(r_2,width/2,height/2);
                rulesWindow.setScene(sc);

                //TODO: make the GIF play
                tutorialView.setFitWidth(width/2);
                tutorialView.setFitHeight(height/2);
                r_2.getChildren().add(tutorialView);
                rulesWindow.show();

            }
        });

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