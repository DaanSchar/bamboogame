package com.university.maastricht.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Circle {

    private int x;
    private int y;

    private Color color;

    private static int segments = 70; // the amount of detail the circle has
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

    /**
     * returns true if circle is pressed
     */
    public boolean isClicked() {
        return isMouseHover() && Gdx.input.isTouched();
    }


    /**
     * return true if the mouse is positioned
     * directly on top of the circle
     */
    public boolean isMouseHover() {
        if (getDistanceToMouse() < radius)
            return true;
        return false;
    }

    /**
     * returns the distance between
     * the mouse and the center of the circle
     */
    private int getDistanceToMouse() {
        int mouseX = Gdx.input.getX() ;
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        return (int) Math.sqrt( (mouseX - x)*(mouseX - x) + (mouseY - y)*(mouseY - y) );
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
