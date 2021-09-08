package com.university.maastricht.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.university.maastricht.components.CircularClickable;
import com.university.maastricht.components.GameObject;

public class Tile {

    private int x;
    private int y;
    private int index;

    private BitmapFont text;
    private CircularClickable view;
    private GameObject g;

    private static int radius = 50;

    public Tile(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        text = new BitmapFont();
        view = new CircularClickable("badlogic.jpg", x, y, radius);
        g = new GameObject("badlogic.jpg", x, y, radius*2, radius*2);
    }

    public void render(SpriteBatch batch) {
        if (view.isMouseHover())
            onHoverEnter();
        else
            onHoverExit();

        if (view.isClicked())
            onClick();


        view.render(batch);
//        text.draw(batch, Integer.toString(index), x, y);
//        g.render(batch);
    }

    private void onHoverEnter() {
//        g.setX(x + 5);
//        g.setHeight(radius*2 + 5);
//        g.setWidth(radius*2 + 5);

        view.setX(x - 5);
        view.setRadius(radius + 5);
    }

    private void onHoverExit() {
//        g.setX(x);
//        g.setHeight(radius*2);
//        g.setWidth(radius*2);
        view.setX(x);
        view.setY(y);
        view.setRadius(radius);
    }

    private void onClick() {
        System.out.println("clicked circle with index " + index);
    }

}
