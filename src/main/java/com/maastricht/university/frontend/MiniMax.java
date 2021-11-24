package com.maastricht.university.frontend;

import com.maastricht.university.frontend.components.HoverableButton;
import com.maastricht.university.frontend.components.tile.TileMap;
import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
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

public class MiniMax extends Main{

    @Override
    public Scene getScene(){
        createTimerThread();
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

        return scene;
    }

    /**
     * This method creates a timer thread that begins the gameloop and makes the agent wait to play.
     */
    public void createTimerThread() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        try{ Thread.sleep(5000); } catch(InterruptedException e) { e.printStackTrace(); }
                        gameloop();
                    }
                },
                1000
        );
    }

    /**
     * This calls the agent while there is no winner
     */
    private void gameloop() {
        while(Factory.getGameState().winner() == 0) {
            //runDoubleAi();
            //runMiniMax();
            runAlphaBeta_vs_random();
        }
        isWinner();
        System.out.println("winner is " + Factory.getGameState().winner());
    }

    /**
     * This uses the AlphaBetaAgent class to determine moves for the agent
     */
    public void runMiniMax(){
        IGameState state =  Factory.getGameState();
        AlphaBetaAgent agent = new AlphaBetaAgent(state, 2, 5);

        agent.move();
        WindowUpdater.update(state);
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

    }

    public void runAlphaBeta_vs_random() {
        IGameState state =  Factory.getGameState();
        AlphaBetaAgent agent2 = new AlphaBetaAgent(state, 2, 4);
        Agent agent = new Agent(state, 1);

        agent.move();
        WindowUpdater.update(state);
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        agent2.move();
        WindowUpdater.update(state);
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    /**
     * This uses the Agent class (our base agent) to determine (random) moves for the agent
     * It makes 2 agents play against each other, both play against each other randomly.
     */
    public void runDoubleAi() {
        IGameState state =  Factory.getGameState();
        Agent agent = new Agent(state, 1);
        Agent agent2 = new Agent(state, 2);

        agent.move();
        WindowUpdater.update(state);
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        agent2.move();
        WindowUpdater.update(state);
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
    }




}

