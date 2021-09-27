package com.maastricht.university.frontend;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Main{
    int width = 800;
    int height = 500;

    public ImageView p1 = new ImageView(new Image("/images/playerone.png"));
    public ImageView p2 = new ImageView(new Image("/images/playertwo.png"));
    public Image bambooBcg = new Image("/images/bamboo.jpeg", width, height, false, true);
    public BackgroundImage bImg = new BackgroundImage(bambooBcg,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
    public Background bGround = new Background(bImg);

    public Scene getScene(){

        TileMap tilemap = new TileMap(width/4,height/6,30);
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

        playground.setBackground(bGround);

        Scene scene = new Scene(playground, width, height);
        return scene;
    }

    //TODO: this method needs to connect to a logic variable that stores the number of groups for p1
    public int p1groups(){
        return 0;
    }

    //TODO: this method needs to connect to a logic variable that stores the number of groups for p2
    public int p2groups(){
        return 0;
    }

    //public static void main(String[] args) {
        //launch();
    //}

}