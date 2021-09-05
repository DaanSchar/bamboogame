package com.university.maastricht.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Circle {

    private int x;
    private int y;
    private int index;

    BitmapFont font;
    private Color color;

    private static int segments = 70; // the amount fo detail the circle has
    private int radius;

    public Circle(int x, int y, int radius, Color color, int index) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        font = new BitmapFont();
        this.index = index;

    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.circle(x, y, radius, segments);
    }

    public void renderText(SpriteBatch batch) {
        font.draw(batch, Integer.toString(index) , x, y);
    }

    // this method works properly as long as we dont resize the window.
    // TODO: handle window resizing
    public boolean isMouseHover() {
        if (getDistanceToMouse() < radius)
            return true;
        return false;
    }

    private int getDistanceToMouse() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        return (int) Math.sqrt( (mouseX - x)*(mouseX - x) + (mouseY - y)*(mouseY - y) );
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
