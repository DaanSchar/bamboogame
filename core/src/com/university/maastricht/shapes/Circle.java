package com.university.maastricht.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.university.maastricht.Game;

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

    private int getDistanceToMouse() {
        float widthRatio =  Game.viewport.getWorldWidth() / Game.viewport.getScreenWidth();
        float heightRatio = Game.viewport.getWorldHeight() / Game.viewport.getScreenHeight();

        int mouseX = (int) (Gdx.input.getX() * widthRatio);
        int mouseY = (int) ((Gdx.graphics.getHeight() - Gdx.input.getY()) * heightRatio);

        return (int) Math.sqrt( (mouseX - x)*(mouseX - x) + (mouseY - y)*(mouseY - y) );
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
