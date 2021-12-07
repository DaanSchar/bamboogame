package com.maastricht.university.frontend;

import com.maastricht.university.frontend.components.HoverableButton;
import com.maastricht.university.frontend.components.tile.TileMap;
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

public class GameScreen {

    public static int width = Factory.getScreenWidth();
    public static int height = Factory.getScreenHeight();

    public ImageView player1Box = new ImageView(new Image("/images/playerone.png"));
    public ImageView player2Box = new ImageView(new Image("/images/playertwo.png"));
    public static ImageView exitView = new ImageView(new Image("/images/button_exit.png"));
    public static ImageView homeView = new ImageView(new Image("/images/home.png"));

    public Image bambooBcg = new Image("/images/bamboo.jpeg", width, height, false, true);
    public BackgroundImage bImg = new BackgroundImage(bambooBcg,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
    public Background bGround = new Background(bImg);

    public static Label p1GroupsLabel = new Label("0");
    public static Label p2GroupsLabel = new Label("0");
    public static Label currentPlayer = new Label("1");

    public static ImageView p1WinnerPopUp = new ImageView(new Image("/images/player_one_wins.gif"));
    public static ImageView p2WinnerPopUp = new ImageView(new Image("/images/player_two_wins.gif"));

    public TileMap tilemap = Factory.getTileMap();
    public Pane playground = tilemap.getTileMapPane();
    public Scene scene = new Scene(playground, width, height);

    /**
     * returns the game screen scene
     */
    public Scene getScene(){
        createPlayerLabels();
        createGroupLabels();
        createCurrentPlayerLabel();
        createExitButton();
        playground.setBackground(bGround);

        return scene;
    }

    /**
     * creates a new window to show the winning player
     */
    public static void showWinnerPopUp(){
        VBox r = new VBox();
        Scene scene = new Scene(r,720,405);
        Stage winnerStage = new Stage();
        createWinnerPopUp();

        IGameState gameState = Factory.getGameState();

        if (gameState.winner() == 1)
            r.getChildren().add(p1WinnerPopUp);
        else if (gameState.winner() == 2)
            r.getChildren().add(p2WinnerPopUp);

        winnerStage.setScene(scene);
        winnerStage.show();
    }

    /**
     * Overrides the p1Text label with the current number of groups for player 1
     */
    public static void updatePlayerLabels(IGameState state) {
        p1GroupsLabel.setText(Integer.toString(state.getTotalGroups(1)));
        p2GroupsLabel.setText(Integer.toString(state.getTotalGroups(2)));
    }

    /**
     * Overrides the current player label with the current player
     */
    public static void updateCurrentPlayerLabel(IGameState state) {
        currentPlayer.setText(currentPlayer(state));
    }

    /**
     * This method uses the GameState interface to return which player is currently playing
     * @return A string with the current player
     */
    public static String currentPlayer(IGameState state){
        String player = state.getPlayerTurn() == 1 ? "Player 1" : "Player 2";

        return player;
    }

    private void createPlayerLabels() {
        player1Box.setLayoutX(-width /4);
        player1Box.setLayoutY(-height/4);
        player1Box.setFitWidth(width/1.35);
        player1Box.setFitHeight(width/2);
        playground.getChildren().add(player1Box);

        player2Box.setLayoutX(width/2);
        player2Box.setLayoutY(-height/4);
        player2Box.setFitWidth(width/1.35);
        player2Box.setFitHeight(width/2);
        playground.getChildren().add(player2Box);
    }

    private void createGroupLabels() {
        p1GroupsLabel.setLayoutY(60.0);
        p1GroupsLabel.setLayoutX(100.0);
        p1GroupsLabel.setFont(new Font("Times New Roman", 25));
        p1GroupsLabel.setTextFill(Color.WHITE);
        playground.getChildren().add(p1GroupsLabel);

        p2GroupsLabel.setLayoutY(60.0);
        p2GroupsLabel.setLayoutX(700.0);
        p2GroupsLabel.setFont(new Font("Times New Roman", 25));
        p2GroupsLabel.setTextFill(Color.WHITE);
        playground.getChildren().add(p2GroupsLabel);
    }

    private void createCurrentPlayerLabel() {
        currentPlayer.setLayoutX(340.0);
        currentPlayer.setLayoutY(30.0);
        currentPlayer.setFont(new Font("Barlow Condensed Bold", 25));
        currentPlayer.setTextFill(Color.BLACK);
        playground.getChildren().add(currentPlayer);
    }

    private void createExitButton() {
        HoverableButton exit = new HoverableButton(50, height - height/12, 20, 20);
        exitView.setFitHeight(40);
        exitView.setFitWidth(80);
        exit.setGraphic(exitView);

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.exit(0); // closes the application
            }
        });

        playground.getChildren().add(exit);
    }

    private static void createWinnerPopUp() {
        // win screen for player 1
        p1WinnerPopUp.setLayoutX(width /2 + 10);
        p1WinnerPopUp.setLayoutY(height /2);
        p1WinnerPopUp.setFitWidth(720);
        p1WinnerPopUp.setFitHeight(405);

        // win screen for player 2
        p2WinnerPopUp.setLayoutX(width /2 + 10);
        p2WinnerPopUp.setLayoutY(height /2);
        p2WinnerPopUp.setFitWidth(720);
        p2WinnerPopUp.setFitHeight(405);
    }











    // -------------- bot vs bot stuff ----------------

    /**
     * This method creates a timer thread that begins the gameloop and makes the agent wait to play.
     */
    public void createTimerThread() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        try{ Thread.sleep(5000); } catch(InterruptedException e) { e.printStackTrace(); }
                        gameLoop();
                    }
                },
                1000
        );
    }

    /**
     * This calls the agent while there is no winner
     */
    private void gameLoop() {
        while(Factory.getGameState().winner() == 0) {
            // run both agents here
        }
        showWinnerPopUp();
    }
}
