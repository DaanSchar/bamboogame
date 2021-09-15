package com.maastricht.university.frontend;

import javafx.scene.control.Button;

public class BambooButton extends Button {

    private double x;
    private double y;

    private double width;
    private double height;

    private String color = "#333333";

    public BambooButton(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        setSize(width, height);
        setButtonPos(x, y);
        hoverEnter();
        hoverExit();
        clickEvent();
    }

    private void setButtonPos(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    private void hoverEnter() {
        this.setOnMouseEntered(e -> {
            updateButtonSizeBy(width/10, height/10);
        });
    }

    private void hoverExit() {
        this.setOnMouseExited(e -> {
            updateButtonSizeBy(0, 0);
        });
    }

    private void clickEvent() {
        this.setOnMousePressed(e -> {
            updateButtonSizeBy(width/6, height/6);
        });
        this.setOnMouseReleased(e -> {
            updateButtonSizeBy(width/10, height/10);
        });
    }

    private void updateButtonSizeBy(double width, double height) {
        setButtonPos(this.x - width/2, this.y - height/2);
        setSize(this.width + width, this.height + height);
    }

    private void setSize(double width, double height) {
        setMaxWidth(width);
        setMinWidth( width);
        setMinHeight(height);
        setMaxHeight(height);
    }


}
