package com.university.maastricht.tile;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.university.maastricht.Game;
import com.university.maastricht.components.Button;

public class Tile {

    private int x;
    private int y;

    private int index;
    private int radius;

    private Button actor;

    public Tile(int x, int y, int radius, final int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        this.radius = radius;

        actor = new Button(Game.spriteSheet.findRegion("ball_red_large"), x, y, 2*radius, 2*radius);
        actor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(index);
            }
        });
    }

    public Button getActor() {
        return actor;
    }
}
