package com.maastricht.university.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LogicTile {

    private int playerColor;
    private int q;
    private int r;

    public LogicTile(int q, int r) {
        playerColor = 0;
        this.q = q;
        this.r = r;
    }

    public int getQ() {
        return this.q;
    }

    public int getR() {
        return this.r;
    }

    /**
     * Returns the colour this tile is coloured with
     * @return the colour of this tile (postive int, 0 if not coloured)
     */
    public int getPlayerColor() {
        return this.playerColor;
    }

    /**
     * Set the colour of the tile
     * @param playerColor the new colour of this tile
     */
    public void setPlayerColour(int playerColor) {
        this.playerColor = playerColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogicTile logicTile = (LogicTile) o;
        return playerColor == logicTile.getPlayerColor() &&
                q == logicTile.getQ() &&
                r == logicTile.getR();
    }

}