package com.university.maastricht.ui.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.university.maastricht.ui.Game;

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


    public boolean isMouseHover() {
        if (getDistanceToMouse() < radius)
            return true;
        return false;
    }

    public boolean isClicked() {
        return isMouseHover() && Gdx.input.isTouched();
    }

    private int getDistanceToMouse() {
        int mouseX = Gdx.input.getX() ;
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        return (int) Math.sqrt( (mouseX - x)*(mouseX - x) + (mouseY - y)*(mouseY - y) );
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
