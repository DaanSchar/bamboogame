package com.maastricht.university.frontend;

import com.maastricht.university.logic.util.interfaces.IGameState;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class Tile {

    private HoverableButton button;

    private double size;
    private double x;
    private double y;
    private int q;
    private int r;

    public Tile(int q, int r, double x, double y, double size)  {
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
//        button.setColor("#121212");
        SVGPath circle = new SVGPath();
        circle.setContent("M25 20" +
                "a5 5 0 1 1-10 0 " +
                "5 5 0 1 1 10 0z");
        button.setShape(circle);
        setClickEvent();
    }

    // here we determine what happens when we click the button
    private void setClickEvent() {
        button.setOnMouseClicked(e -> {
            IGameState game = Factory.getGameState();
            game.move(q,r,Factory.getGameState().getPlayerTurn());

            int playerColor = game.getPlayerColorOfTile(q,r);
            if (playerColor == 1)
                button.setColor("#4d9de0");
            if (playerColor == 2)
                button.setColor("#E15554");
            System.out.println(game.getTotalGroups(playerColor));
        });
    }

    public Button getButton() {
        return button;
    }
}
