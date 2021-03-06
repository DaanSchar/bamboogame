package com.maastricht.university.frontend.scenes;

import com.maastricht.university.frontend.Factory;
import com.maastricht.university.frontend.components.HoverableButton;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class Menu {

    private final double width = Factory.getScreenWidth();
    private final double height = Factory.getScreenHeight();

    private final BorderPane borderPane = new BorderPane();
    private final Scene scene = new Scene(borderPane, width, height);

    private final Image backgroundImage = new Image("/images/bamboo.jpeg", width, height, false, true);

    private final ImageView title = new ImageView(new Image("/images/bambooText.png"));
    private final ImageView humanView = new ImageView(new Image("/images/button_human.png"));
    private final ImageView minimaxView = new ImageView(new Image("/images/v_minimax.png"));
    private final ImageView nnView = new ImageView(new Image("/images/v_NN.png"));
    private final ImageView exitView = new ImageView(new Image("/images/button_exit.png"));
    private final ImageView rulesView = new ImageView(new Image("/images/button_rules.png"));
    private final ImageView musicView = new ImageView(new Image("/images/MUSIC2.png"));
    private final ImageView hybridView = new ImageView(new Image("/images/button_hybrid.png"));

    public Menu() {
        createBackground();
        createTitle();
        createHumanVHumanButton();
        createMiniMaxButton();
        createNNButton();
        createExitButton();
        createRulesButton();
        createMusicButton();
        createHybridButton();
    }

    public Scene getScene() {
        return scene;
    }

    private void createBackground() {
        BackgroundImage bImg = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        borderPane.setBackground(new Background(bImg));
    }

    private void createTitle() {
        title.setLayoutX(width / 6);
        title.setLayoutY(-height/6);
        title.setFitWidth(width/1.5);
        title.setFitHeight(width/2);
        borderPane.getChildren().add(title);
    }

    private void createHumanVHumanButton() {
        HoverableButton human = new HoverableButton(width/2,height/2.5 , 20, 20);
        humanView.setFitHeight(55);
        humanView.setFitWidth(150);
        human.setGraphic(humanView);

        human.setOnAction(e -> {
            Factory.setGameMode(0);
            moveToGameScreen(e);
        });

        borderPane.getChildren().add(human);
    }

    private void createMiniMaxButton() {
        HoverableButton minimaxButton = new HoverableButton(width / 2, (height-height/2.4)-20, 20, 20);
        minimaxView.setFitHeight(55);
        minimaxView.setFitWidth(150);
        minimaxButton.setGraphic(minimaxView);

        minimaxButton.setOnAction(e -> {
            Factory.setGameMode(1);
            Factory.setAgentType(1);
            moveToGameScreen(e);
        });

        borderPane.getChildren().add(minimaxButton);
    }

    private void createNNButton() {
        HoverableButton nnButton = new HoverableButton(width / 2, (height - height/4.5)-45, 20, 20);
        nnView.setFitHeight(55);
        nnView.setFitWidth(150);
        nnButton.setGraphic(nnView);

        nnButton.setOnAction(e -> {
            Factory.setGameMode(1);
            Factory.setAgentType(0);
            moveToGameScreen(e);
        });

        borderPane.getChildren().add(nnButton);
    }
    private void createHybridButton() {
        HoverableButton hyButton = new HoverableButton(width / 2, (height - height/6.5)-5, 20, 20);
        hybridView.setFitHeight(55);
        hybridView.setFitWidth(150);
        hyButton.setGraphic(hybridView);

        hyButton.setOnAction(e -> {
            Factory.setGameMode(1);
            Factory.setAgentType(2);
            moveToGameScreen(e);
        });

        borderPane.getChildren().add(hyButton);
    }
    private void createExitButton() {
        HoverableButton exit = new HoverableButton(50, height - height/16, 20, 20);
        exitView.setFitHeight(40);
        exitView.setFitWidth(80);
        exit.setGraphic(exitView);
        exit.setOnAction(e -> System.exit(0));

        borderPane.getChildren().add(exit);
    }

    private void createRulesButton() {
        HoverableButton rules = new HoverableButton(width - width / 16, height / 16, 20, 20);
        rulesView.setFitHeight(40);
        rulesView.setFitWidth(80);
        rules.setGraphic(rulesView);

        rules.setOnAction(e -> showRulesPopUp());

        borderPane.getChildren().add(rules);
    }

    private void createMusicButton() {
        HoverableButton music = new HoverableButton(width - width / 16, (height / 16)+50, 20, 20);
        musicView.setFitHeight(40);
        musicView.setFitWidth(80);
        music.setGraphic(musicView);

        music.setOnAction(e -> {
            String path = "src/main/resources/music/music.mp3";
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        });

        borderPane.getChildren().add(music);

    }

    private void showRulesPopUp() {
        Stage rulesWindow = new Stage();

        WebView webview = new WebView();
        String tutorialUrl = "https://www.youtube.com/embed/uVcDO8EmDCs";
        webview.getEngine().load(tutorialUrl);

        VBox box = new VBox(webview);
        Scene r = new Scene(box,width/1.5, height/2 );

        rulesWindow.setScene(r);
        rulesWindow.show();
    }

    private void moveToGameScreen(ActionEvent e) {
        Stage stage = getStageFromAction(e);
        stage.setScene(Factory.getGameScene());
        stage.show();
    }

    /**
     * Gets the stage from the action event.
     * An action event contains a reference to the stage. this
     * way we can get the stage from anywhere where an action occurs
     */
    private Stage getStageFromAction(ActionEvent e) {
        return (Stage) ((Node)e.getSource()).getScene().getWindow();
    }

}