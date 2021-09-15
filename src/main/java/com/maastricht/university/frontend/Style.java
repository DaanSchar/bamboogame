package com.maastricht.university.frontend;

import java.util.Arrays;

public class Style {

    private String[] styles;

    public Style(double size) {
        initStyle();
        setSize(size);
        setColor("#333333");
        setRadius(0);
    }

    public Style(double size, String color) {
        initStyle();
        setSize(size);
        setColor(color);
        setRadius(0);
    }

    public Style(double size, String color, String shape) {
        initStyle();
        setSize(size);
        setColor(color);
        setShape(shape);
    }

    private void initStyle() {
        styles = new String[5];

        for (String style : styles)
            style = "";
    }

    public void setSize(double size) {
        styles[0] = "-fx-min-width:  " + size + "px; " +
                    "-fx-min-height:  " + size + "px; " +
                    "-fx-max-width:  " + size + "px; " +
                    "-fx-max-height:  " + size + "px; ";
    }

    public void setColor(String color) {
        styles[1] = "-fx-background-color: " + color + "; ";
    }

    public void setRadius(double size) {
        styles[2] = "-fx-background-radius: " + size + "em; ";
    }

    public void setShape(String shape) {
        styles[3] = "-fx-shape: " + shape + "; ";
    }

    public String toString() {
        String styleList = "";

        for (String style : styles)
            styleList += style + " ";

        return styleList;
    }
}
