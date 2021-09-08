package com.university.maastricht.components;

public interface Clickable {
    /**
     * returns true if is pressed
     */
    boolean isClicked();

    /**
     * return true if the mouse is positioned
     * directly on top of the object
     */
    boolean isMouseHover();

}
