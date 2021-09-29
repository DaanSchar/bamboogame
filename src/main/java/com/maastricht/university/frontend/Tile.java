package com.maastricht.university.frontend;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class Tile {

    private HoverableButton button;

    private int index;
    private double size;
    private double x;
    private double y;
    private int q;
    private int r;

    private boolean isClicked;

    public Tile(int q, int r, double x, double y, double size, int index)  {
        this.index = index;
        this.size = size;
        this.x = x;
        this.y = y;
        this.q = q;
        this.r = r;

        initButton();
    }

    // here we create a nice looking circular button
    private void initButton() {
        button = new HoverableButton(x, y, size, size);
//        button.setColor("#333333");
        SVGPath circle = new SVGPath();
        circle.setContent("M25 20" +
                "a5 5 0 1 1-10 0 " +
                "5 5 0 1 1 10 0z");
        button.setShape(circle);
        button.setText(Integer.toString(index));
        button.setTextFill(Color.WHITE);
        setClickEvent();
    }

    // here we determine what happens when we click the button
    private void setClickEvent() {
        button.setOnMouseClicked(e -> {
            System.out.println(this.index);
            isClicked = !isClicked;

            if (isClicked)
                Factory.getGameState().move(q,r,Factory.getGameState().getPlayerTurn());

        });
    }

    public Button getButton() {
        return button;
    }
}
