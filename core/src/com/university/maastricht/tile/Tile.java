package com.university.maastricht.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.university.maastricht.shapes.Circle;

public class Tile {

    private int x;
    private int y;
    private int index;

    private Circle outline;
    private Circle center;

    private static int radius = 90;

    public Tile(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;

        outline = new Circle(x, y, radius, new Color(0,0,0,0), index);
        center = new Circle(x, y, radius-3, new Color(1,1,1,0), index);
    }


    public void render(ShapeRenderer renderer) {
        if (center.isMouseHover())
            onHoverEnter();
        else
            onHoverExit();

        outline.render(renderer);
        center.render(renderer);
    }

    private void onHoverEnter() {
        center.setRadius(radius + 2);
        outline.setRadius(radius + 5);
    }

    private void onHoverExit() {
        outline.setRadius(radius);
        center.setRadius(radius - 3);
    }

    public void renderText(SpriteBatch batch) {
        center.renderText(batch);
    }
}
