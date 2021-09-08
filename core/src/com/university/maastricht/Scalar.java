package com.university.maastricht;

import com.badlogic.gdx.Gdx;

public class Scalar {


    /**
     * returns the amount the screen has scaled in
     * the vertical direction compared to the initial screen size;
     */
    public static double getHeight() {

        int screen = Gdx.graphics.getHeight();
        int world = Game.WINDOW_HEIGHT;

        return (double)screen/(double)world;
    }

    /**
     * returns the amount the screen has scaled in
     * the horizontal direction compared to the initial screen size;
     */
    public static double getWidth() {

        int screen = Gdx.graphics.getWidth();
        int world = Game.WINDOW_WIDTH;

        return (double)screen/(double)world;
    }


}
