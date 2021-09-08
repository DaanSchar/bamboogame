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
        this.width = width;
        this.height = height;
        texture = new Texture(textureUrl);
    }

    public GameObject(String textureUrl, int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(textureUrl);
    }

    public GameObject(String textureUrl,int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        texture = new Texture(textureUrl);
    }

    public void render(SpriteBatch batch) {
        if (this.width == 0 || this.height == 0)
            batch.draw(texture, x, y);
        else
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

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
