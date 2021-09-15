package com.maastricht.university.frontend;

import javafx.scene.control.Button;

public class Tile {

    private Button button;

    private int index;
    private int size;

    private int x;
    private int y;

    public Tile(int x, int y, int size, int index) {
        this.index = index;
        this.size = size;
        this.x = x;
        this.y = y;

        createButton(size);
    }

    private void createButton(int size) {
        button = new Button(Integer.toString(index));
        setButtonPos(x, y);
        setButtonStyle(size);
        setClickEvent();
        setHoverEnter();
        setHoverExit();

    }

    private void setButtonPos(int x, int y) {
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    private void setButtonStyle(int size) {
        button.setStyle(
                "-fx-background-radius: " + size + "em; " +
                        "-fx-min-width:  " + size + "px; " +
                        "-fx-min-height:  " + size + "px; " +
                        "-fx-max-width:  " + size + "px; " +
                        "-fx-max-height:  " + size + "px;" +
                        "-fx-background-color: #333333 ; "
        );
    }

    private void setHoverEnter() {
        button.setOnMouseEntered(e -> {
            updateButtonSizeBy(size/10);
        });
    }

    private void setHoverExit() {
        button.setOnMouseExited(e -> {
            updateButtonSizeBy(0);
        });
    }

    private void setClickEvent() {
        button.setOnMouseClicked(e -> {
            System.out.println(this.index);
        });

        button.setOnMousePressed(e -> {
            updateButtonSizeBy(size/6);
        });
        button.setOnMouseReleased(e -> {
            updateButtonSizeBy(size/10);
        });
    }

    private void updateButtonSizeBy(int size) {
        setButtonPos(this.x - size/2, this.y - size/2);
        setButtonStyle(this.size + size);
    }

    public Button getButton() {
        return button;
    }
}
