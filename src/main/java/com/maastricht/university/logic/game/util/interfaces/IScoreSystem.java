package com.maastricht.university.logic.game.util.interfaces;

public interface IScoreSystem {

    /**
     *
     * @param playerColour the colour of the player you want the score off
     * @return the score of that player
     */
    public int getPlayerScore(int playerColour);
}
