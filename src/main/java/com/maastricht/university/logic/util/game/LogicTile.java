package com.maastricht.university.logic.util.game;

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
     * @return the colour of this tile (positive int, 0 if not coloured)
     */
    public int getPlayerColor() {
        return this.playerColor;
    }

    /**
     * Set the colour of the tile.
     * current color of the tile must be 0.
     * @param playerColor the new colour of this tile
     */
    public void setPlayerColour(int playerColor) {
        try {
            if (this.playerColor != 0)
                throw new IllegalAccessException("this tile is already colored with " + this.playerColor +". you cannot give it a different color");

            this.playerColor = playerColor;

        } catch (Exception e) {
            System.out.println(e);
        }
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

    @Override
    public String toString() {
        return playerColor + "";
    }
}