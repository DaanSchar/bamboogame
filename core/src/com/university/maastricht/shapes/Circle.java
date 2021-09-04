package com.university.maastricht.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Circle {

    private int x;
    private int y;

    private Color color;

    private static int segments = 70; // the amount fo detail the circle has
    private int radius;

    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.circle(x, y, radius, segments);
    }

}
