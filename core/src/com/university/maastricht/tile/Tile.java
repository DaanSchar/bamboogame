package com.university.maastricht.tile;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.university.maastricht.Game;
import com.university.maastricht.components.CircularClickable;

public class Tile {

    private int x;
    private int y;
    private int index;

    private BitmapFont text;
    private CircularClickable gameObject;

    private static int radius = 50;

    public Tile(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        text = new BitmapFont();
        gameObject = new CircularClickable(Game.spriteSheet, "ball_blue_large", x, y, radius);
    }

    public void render(SpriteBatch batch) {
        if (gameObject.isMouseHover())
            onHoverEnter();
        else
            onHoverExit();

        if (gameObject.isClicked())
            onClick();

        gameObject.render(batch);
        text.draw(batch, Integer.toString(index),x + radius, y + radius);
    }

    private void onHoverEnter() {
        int dSize = 5;
        gameObject.setX(x - dSize);
        gameObject.setY(y - dSize);
        gameObject.setRadius(radius + dSize);
    }

    private void onHoverExit() {
        gameObject.setX(x);
        gameObject.setY(y);
        gameObject.setRadius(radius);
    }

    private void onClick() {
        System.out.println("clicked circle with index " + index);
    }

    public CircularClickable getGameObject() {
        return gameObject;
    }
}
