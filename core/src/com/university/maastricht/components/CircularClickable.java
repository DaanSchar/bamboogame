package com.university.maastricht.components;

import com.badlogic.gdx.Gdx;

public class CircularClickable extends GameObject implements Clickable{

    private int x;
    private int y;
    private int radius;


    public CircularClickable(String textureUrl, int x, int y, int radius) {
        super(textureUrl, x-radius, y-radius, 2*radius, 2*radius);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public boolean isClicked() {
        return isMouseHover() && Gdx.input.isTouched();
    }

    @Override
    public boolean isMouseHover() {
        if (getDistanceToMouse() < radius)
            return true;
        return false;
    }

    private int getDistanceToMouse() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        return (int) Math.sqrt((mouseX - getX())*(mouseX - getX()) + (mouseY - this.getY())*(mouseY - this.getY()) );
    }


    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        super.setHeight(2*radius);
        super.setWidth(2*radius);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
