package com.maastricht.university.frontend;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class HoverableButton extends Button {

    public HoverableButton(double x, double y, double width, double height) {
        initButton(x, y, width, height);
        hoverEnter();
        hoverExit();
        clickEvent();
    }

    private void initButton(double x, double y, double width, double height) {
        super.setMaxWidth(width);
        super.setMinWidth(width);
        super.setMinHeight(height);
        super.setMaxHeight(height);
        super.setLayoutX(x);
        super.setLayoutY(y);
    }


    private void hoverEnter() {
        super.setOnMouseEntered(e -> {
            sizeTransition(1.1, 1.1, 0.05);
        });
    }

    private void hoverExit() {
        super.setOnMouseExited(e -> {
            sizeTransition(1, 1, 0.2);
        });
    }

    private void clickEvent() {
        super.setOnMousePressed(e -> {
            sizeTransition(1.15, 1.15, 0.05);
        });
        super.setOnMouseReleased(e -> {
            sizeTransition(1.1, 1.1, 0.15);
        });
    }

    // nice looking animation for transitioning between sizes
    private void sizeTransition(double width, double height, double time) {
        ScaleTransition transition = new ScaleTransition();
        transition.setDuration(Duration.seconds(time));
        transition.setNode(this);
        transition.setToX(width);
        transition.setToY(height);
        transition.play();
    }

    public void setColor(String color) {
        setStyle("-fx-background-color: " + color + "; ");
    }


}
