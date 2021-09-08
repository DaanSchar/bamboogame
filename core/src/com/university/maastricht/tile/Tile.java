package com.university.maastricht.tile;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.university.maastricht.components.CircularClickable;

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
        view = new CircularClickable("badlogic.jpg", x, y, radius);
        if (index == 1)
            view.setRadius(100);

    }

    public void render(SpriteBatch batch) {
        if (view.isMouseHover())
            onHoverEnter();
        else
            onHoverExit();

        if (view.isClicked())
            onClick();

        view.render(batch);
        text.draw(batch, Integer.toString(index) , x, y);
    }

    private void onHoverEnter() {
        view.setRadius(radius + 5);
    }

    private void onHoverExit() {
        view.setRadius(radius);
    }

    private void onClick() {
        System.out.println("clicked circle with index " + index);
    }

}
