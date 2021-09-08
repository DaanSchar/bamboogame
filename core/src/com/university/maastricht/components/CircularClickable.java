package com.university.maastricht.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class CircularClickable extends GameObject implements Clickable{

    private int radius;

    public CircularClickable(TextureAtlas atlas, String textureName, int x, int y, int radius) {
        super(atlas, textureName, x-radius, y-radius, 2*radius, 2*radius);
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
        int mouseX = Gdx.input.getX() - radius;
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY() - radius;

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
}
