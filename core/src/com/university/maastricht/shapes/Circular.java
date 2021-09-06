package com.university.maastricht.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.university.maastricht.components.CircularClickable;

public class Circular extends CircularClickable {

    private Color color;
    private static int segments = 70; // the amount of detail the circle has

    public Circular(int x, int y, int radius, Color color) {
        super(x,y,radius);
        this.color = color;
    }


    public void render(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.circle(super.getX(), super.getY(),super.getRadius(), segments);
    }
}
