package com.university.maastricht.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameObject {

    private Sprite sprite;

    private int width;
    private int height;

    private int x;
    private int y;

    public GameObject(TextureAtlas atlas, String textureName) {
        this.sprite = new Sprite(atlas.findRegion(textureName));
    }

    public GameObject(TextureAtlas atlas, String textureName, int x, int y) {
        this.x = x;
        this.y = y;
        this.sprite = new Sprite(atlas.findRegion(textureName));
    }

    public GameObject(TextureAtlas atlas, String textureName,int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = new Sprite(atlas.findRegion(textureName));
    }

    //TODO: add position and width/height
    public void render(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.setOrigin(width/2, height/2);

        if (!(this.width == 0 || this.height == 0))
            sprite.setSize(width, height);

        sprite.draw(batch);
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

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
