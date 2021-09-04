package com.university.maastricht.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.university.maastricht.shapes.Circle;

public class Tile {

    private int x;
    private int y;

    private Circle outline;
    private Circle center;

    private static int radius = 90;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;

        createCircles();
    }

    private void createCircles() {
        outline = new Circle(x, y, radius, new Color(0,0,0,0));
        center = new Circle(x, y, radius-2, new Color(1,1,1,0));
    }

    public void render(ShapeRenderer renderer) {
        outline.render(renderer);
        center.render(renderer);
    }
}
