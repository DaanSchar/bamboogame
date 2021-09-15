package com.maastricht.university.frontend;

import javafx.scene.control.Button;

public class Tile {

    private int index;
    private Button button;

    private int x;
    private int y;

    public Tile(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;

        createButton();
    }

    private void createButton() {
        button = new Button(Integer.toString(index));
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    public Button getButton() {
        return button;
    }
}
