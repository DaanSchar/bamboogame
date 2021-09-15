package com.university.maastricht.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.university.maastricht.Game;
import com.university.maastricht.components.Button;
import com.university.maastricht.components.GameObject;

public class Tile {

    private int x;
    private int y;

    private int index;
    private int radius;
    private boolean pressed = false;

    private Button actor;
    private GameObject layer1;

    public Tile(int x, int y, int radius, final int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        this.radius = radius;

        layer1 = new GameObject(Game.spriteSheet, "block_square",x - radius*3/10, y - radius*3/10, 2*radius, 2*radius);
        actor = new Button(Game.spriteSheet.findRegion("ball_red_large"), x, y, (int)(2*(radius*0.7)), (int)(2*(radius*0.7)));
        actor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(index);
                pressed = !pressed;
                if (pressed)
                    setTexture(Game.spriteSheet.findRegion("ball_blue_large"));
                else
                    setTexture(Game.spriteSheet.findRegion("ball_red_large"));
            }
        });
    }

    public void render(SpriteBatch batch) {
        layer1.render(batch);
    }

    public Button getActor() {
        return actor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

    public void setTexture(TextureRegion region) {
        actor.setDrawable(new TextureRegionDrawable(region));
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
