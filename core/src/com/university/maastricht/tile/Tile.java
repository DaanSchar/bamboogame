package com.university.maastricht.tile;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.university.maastricht.Game;
import com.university.maastricht.components.CircularClickable;
import com.university.maastricht.components.GameObject;

public class Tile {

    private int x;
    private int y;
    private int index;

    private BitmapFont text;
    private CircularClickable view;

    private static int radius = 50;

    public Tile(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        text = new BitmapFont();
        view = new CircularClickable(Game.spriteSheet, "hole_large_end", x, y, radius);
    }

    public void render(SpriteBatch batch) {
        if (view.isMouseHover())
            onHoverEnter();
        else
            onHoverExit();

        if (view.isClicked())
            onClick();

        view.render(batch);
        text.draw(batch, Integer.toString(index),x + radius, y + radius);
    }

    private void onHoverEnter() {
        int dSize = 5;
        view.setX(x - dSize);
        view.setY(y - dSize);
        view.setRadius(radius + dSize);
    }

    private void onHoverExit() {
        view.setX(x);
        view.setY(y);
        view.setRadius(radius);
    }

    private void onClick() {
        System.out.println("clicked circle with index " + index);
    }

}
