package com.university.maastricht;

import com.badlogic.gdx.Gdx;

public class Scalar {

    public static double getWidth() {

        int screen = Gdx.graphics.getWidth();
        int world = 1280;

        return (double)screen/(double)world;
    }

    public static double getHeight() {

        int screen = Gdx.graphics.getHeight();
        int world = 720;

        return (double)screen/(double)world;
    }

}
