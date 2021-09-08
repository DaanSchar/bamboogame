package com.university.maastricht.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObject {

    private Texture texture;

    private int width;
    private int height;

    private int x;
    private int y;

    public GameObject(String textureUrl) {
        this.texture = new Texture(textureUrl);
    }

    public GameObject(String textureUrl, int width, int height) {
        this.width = width;
        this.height = height;
        texture = new Texture(textureUrl);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
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
}
