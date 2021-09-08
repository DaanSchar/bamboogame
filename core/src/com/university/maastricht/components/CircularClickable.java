package com.university.maastricht.components;

import com.badlogic.gdx.Gdx;

public class CircularClickable extends GameObject implements Clickable{

    private int radius;

    public CircularClickable(String textureUrl, int x, int y, int radius) {
        super(textureUrl, x, y, radius, radius);
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
        int mouseX = Gdx.input.getX() ;
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        return (int) Math.sqrt( (mouseX - getX())*(mouseX - getX()) + (mouseY - getY())*(mouseY - getY()) );
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        super.setHeight(radius);
        super.setWidth(radius);
    }
}
