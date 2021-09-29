package com.maastricht.university.frontend;


import com.maastricht.university.logic.game.GameState;
import com.maastricht.university.logic.util.interfaces.IGameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Main{
    int width = 800;
    int height = 500;

    public ImageView p1 = new ImageView(new Image("/images/playerone.png"));
    public ImageView p2 = new ImageView(new Image("/images/playertwo.png"));
    public ImageView exitView = new ImageView(new Image("/images/button_exit.png"));
    public Image bambooBcg = new Image("/images/bamboo.jpeg", width, height, false, true);
    public BackgroundImage bImg = new BackgroundImage(bambooBcg,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
    public Background bGround = new Background(bImg);

    public Scene getScene(){

        TileMap tilemap = new TileMap(9,width/4,height/6,30);
        Pane playground = tilemap.getTileMapPane();

        //add labels for player stats to screen
        p1.setLayoutX(-width /4);
        p1.setLayoutY(-height/4);
        p1.setFitWidth(width/1.35);
        p1.setFitHeight(width/2);
        playground.getChildren().add(p1);

        p2.setLayoutX(width/2);
        p2.setLayoutY(-height/4);
        p2.setFitWidth(width/1.35);
        p2.setFitHeight(width/2);
        playground.getChildren().add(p2);

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
        Factory factory = new Factory();
        IGameState gameState = factory.getGameState();

        return gameState.getTotalGroups(1);
    }


    public int p2groups(){
        Factory factory = new Factory();
        IGameState gameState = factory.getGameState();

        return gameState.getTotalGroups(2);
    }

    public static void main(String[]args){

    }


}