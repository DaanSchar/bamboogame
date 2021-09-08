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

    private int radius;

    public Tile(int x, int y, int radius, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        this.radius = radius;

        text = new BitmapFont();
        gameObject = new CircularClickable(Game.spriteSheet, "ball_blue_large", x, y, radius);
    }

    public void render(SpriteBatch batch) {
        mouseEvent();
        gameObject.render(batch);
        text.draw(batch, Integer.toString(index),x + radius, y + radius);
    }

    private void mouseEvent() {
        if (gameObject.isMouseHover())
            onHoverEnter();
        else
            onHoverExit();

        if (gameObject.isClicked())
            onClick();
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
        gameObject.setSprite(Game.spriteSheet, "ball_red_large");
    }
}
