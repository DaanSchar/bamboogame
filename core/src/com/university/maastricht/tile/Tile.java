package com.university.maastricht.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.university.maastricht.shapes.Circle;

public class Tile {

    private int x;
    private int y;
    private int index;

    private BitmapFont font;

    private Circle outline;
    private Circle center;

    private static int radius = 50;

    public Tile(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        font = new BitmapFont();

        outline = new Circle(x, y, radius, new Color(0,0,0,0));
        center = new Circle(x, y, radius-3, new Color(1,1,1,0));
    }


    public void render(ShapeRenderer renderer) {
        if (center.isMouseHover())
            onHoverEnter();
        else
            onHoverExit();

        if (center.isClicked())
            onClick();

        outline.render(renderer);
        center.render(renderer);
    }

    private void onHoverEnter() {
        center.setRadius(radius + 2);
        outline.setRadius(radius + 5);
    }

    private void onHoverExit() {
        center.setRadius(radius - 3);
        outline.setRadius(radius);
    }

    private void onClick() {
        System.out.println("clicked circle with index " + index);
    }

    public void renderText(SpriteBatch batch) {
        font.draw(batch, Integer.toString(index) , x, y);
    }
}
