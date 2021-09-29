package com.maastricht.university.frontend;


import com.maastricht.university.logic.game.GameState;
import com.maastricht.university.logic.util.interfaces.IGameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main{
    int width = 800;
    int height = 500;

    public ImageView p1 = new ImageView(new Image("/images/playerone.png"));
    public ImageView p2 = new ImageView(new Image("/images/playertwo.png"));
    public ImageView backView = new ImageView(new Image("/images/button_back.png"));
    public ImageView exitView = new ImageView(new Image("/images/button_exit.png"));
    public Image bambooBcg = new Image("/images/bamboo.jpeg", width, height, false, true);
    public BackgroundImage bImg = new BackgroundImage(bambooBcg,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
    public Background bGround = new Background(bImg);

    public Label p1Text = new Label(Integer.toString(p1groups()));
    public Label p2Text = new Label(Integer.toString(p2groups()));

    public Scene getScene(){

        TileMap tilemap = new TileMap(9,width/4,height/6,30);
        Pane playground = tilemap.getTileMapPane();

        //add labels for player stats to screen
        p1.setLayoutX(-width /4);
        p1.setLayoutY(-height/4);
        p1.setFitWidth(width/1.35);
        p1.setFitHeight(width/2);
        playground.getChildren().add(p1);

        //add the group number to the screen
        p1Text.setLayoutY(60.0);
        p1Text.setLayoutX(100.0);
        p1Text.setFont(new Font("Times New Roman", 25));
        p1Text.setTextFill(Color.WHITE);
        playground.getChildren().add(p1Text);

        p2.setLayoutX(width/2);
        p2.setLayoutY(-height/4);
        p2.setFitWidth(width/1.35);
        p2.setFitHeight(width/2);
        playground.getChildren().add(p2);


        p2Text.setLayoutY(60.0);
        p2Text.setLayoutX(700.0);
        p2Text.setFont(new Font("Times New Roman", 25));
        p2Text.setTextFill(Color.WHITE);
        playground.getChildren().add(p2Text);

        /*
        Button p2Groups = new Button();
        p2Groups.setLayoutY(65.0);
        p2Groups.setLayoutX(700.0);
        playground.getChildren().add(p2Groups);
         */

        HoverableButton exit = new HoverableButton(50, height - height/12, 20, 20);
        exitView.setFitHeight(40);
        exitView.setFitWidth(80);
        exit.setGraphic(exitView);
        playground.getChildren().add(exit);

        //if the exit button is clicked, it will close the program
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e)
            {
                System.exit(0);
            }
        });

        playground.setBackground(bGround);
        Scene scene = new Scene(playground, width, height);
        return scene;
    }


    public int p1groups(){
        IGameState gameState = Factory.getGameState();

        return gameState.getTotalGroups(1);
    }

    public int p2groups(){
        IGameState gameState = Factory.getGameState();

        return gameState.getTotalGroups(2);
    }


    public static void main(String[]args){

    }


}