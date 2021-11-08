package com.maastricht.university.frontend.components.tile;

public enum TileColor {

    PLAYER1("#4d9de0"),
    PLAYER2("#E15554"),
    LEGAL("#7AE967"),
    NONE("#fff");

    private String color;

    TileColor(String color){
        this.color = color;
    }

    public String get() {
        return this.color;
    }
}
