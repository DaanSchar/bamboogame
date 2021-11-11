package com.maastricht.university.frontend;

import com.maastricht.university.frontend.components.HoverableButton;
import com.maastricht.university.frontend.components.tile.TileMap;
import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main{
    private int width = Factory.getScreenWidth();
    private int height = Factory.getScreenHeight();

    private static int sWidth = Factory.getScreenWidth();
    private static int sHeight = Factory.getScreenHeight();

    private ImageView p1 = new ImageView(new Image("/images/playerone.png"));
    private ImageView p2 = new ImageView(new Image("/images/playertwo.png"));
    private static ImageView exitView = new ImageView(new Image("/images/button_exit.png"));
    private static ImageView homeView = new ImageView(new Image("/images/home.png"));

    private Image bambooBcg = new Image("/images/bamboo.jpeg", width, height, false, true);
    private BackgroundImage bImg = new BackgroundImage(bambooBcg,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
    private Background bGround = new Background(bImg);

    public static Label p1Text = new Label(Integer.toString(p1groups()));
    public static Label p2Text = new Label(Integer.toString(p2groups()));
    public static Label currentPlayer = new Label(currentPlayer());

    private static ImageView oneWin = new ImageView(new Image("/images/player_one_wins.gif"));
    private static ImageView twoWin = new ImageView(new Image("/images/player_two_wins.gif"));

    public Scene getScene(){

        TileMap tilemap = Factory.getTileMap();
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

        //display current player
        currentPlayer.setLayoutX(340.0);
        currentPlayer.setLayoutY(30.0);
        currentPlayer.setFont(new Font("Barlow Condensed Bold", 25));
        currentPlayer.setTextFill(Color.BLACK);
        playground.getChildren().add(currentPlayer);

        HoverableButton exit = new HoverableButton(50, height - height/12, 20, 20);
        exitView.setFitHeight(40);
        exitView.setFitWidth(80);
        exit.setGraphic(exitView);
        playground.getChildren().add(exit);

        //if the exit button is clicked, it will close the program
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

        playground.setBackground(bGround);
        Scene scene = new Scene(playground, width, height);

        // CODE ADDED FOR 2 AI's RUNNING
        // createTimerThread(); // uncomment this line to have 2 ai's fight it out

        return scene;
    }

    public void createTimerThread() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        gameloop();
                    }
                },
                1000
        );
    }

    private void gameloop() {
        while(Factory.getGameState().winner() == 0) {
            runDoubleAi();
        }
        System.out.println("winner is " + Factory.getGameState().winner());
    }

    public void runDoubleAi() {
        IGameState state =  Factory.getGameState();
        Agent agent = new Agent(state, 1);
        Agent agent2 = new Agent(state, 2);

        agent.move();
        WindowUpdater.update(Factory.getGameState());
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        agent2.move();
        WindowUpdater.update(Factory.getGameState());
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    /**
     * This method uses the GameState interface to return which player is currently playing
     * @return A string with the current player
     */
    public static String currentPlayer(){
        IGameState gameState = Factory.getGameState();
        if(gameState.getPlayerTurn() == 1){
            return "PLAYER 1";
        }else{
            return "PLAYER 2";
        }
    }

    /**
     * This method uses the GameState interface to return the number of groups for player 1
     * @return number of groups for player 1
     */
    public static int p1groups(){
        IGameState gameState = Factory.getGameState();
        return gameState.getTotalGroups(1);
    }
    /**
     * This method uses the GameState interface to return the number of groups for player 2
     * @return number of groups for player 2
     */
    public static int p2groups(){
        IGameState gameState = Factory.getGameState();
        return gameState.getTotalGroups(2);
    }

    public static void isWinner(){
        IGameState gameState = Factory.getGameState();

        VBox r = new VBox();
        Scene scene = new Scene(r,720,405);
        Stage winnerStage = new Stage();

        if(gameState.winner() == 1){
           oneWin.setLayoutX(sWidth /2 + 10);
           oneWin.setLayoutY(sHeight /2);
           oneWin.setFitWidth(720);
           oneWin.setFitHeight(405);

           r.getChildren().add(oneWin);

        }else if(gameState.winner() == 2){
            twoWin.setLayoutX(sWidth /2 + 10);
            twoWin.setLayoutY(sHeight /2);
            twoWin.setFitWidth(720);
            twoWin.setFitHeight(405);

            r.getChildren().add(twoWin);
        }

        winnerStage.setScene(scene);
        winnerStage.show();
    }


}