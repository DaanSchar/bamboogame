package com.university.maastricht.shapes;

import com.badlogic.gdx.Gdx;
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
        if (getDistanceToMouse() < super.getRadius())
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

        return (int) Math.sqrt( (mouseX - super.getX())*(mouseX - super.getX()) + (mouseY - super.getY())*(mouseY - super.getY()) );
    }
}
