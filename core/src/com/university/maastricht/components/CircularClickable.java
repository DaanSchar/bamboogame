package com.university.maastricht.components;

import com.badlogic.gdx.Gdx;

public class CircularClickable {

    private int x;
    private int y;
    private int radius;

    public CircularClickable(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
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



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
