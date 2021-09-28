package com.maastricht.university.logic.util.interfaces;

import com.maastricht.university.logic.game.Board;
import com.maastricht.university.logic.game.TileGroup;

import java.util.List;

public interface IGameState {

    /**
     * performs a move, which will only execute when it is legal.
     * this is what you have to call whenever a player clicks on a tile.
     *
     * @param q vertical coordinate which must be inside the hexagon
     * @param r horizontal coordinate which must be inside the hexagon
     * @param playerColor identification of the player, which must be > 0
     */
    public void move(int q, int r, int playerColor);

    /**
     * checks if a move is legal.
     * if you check this for every tile, you can display which moves
     * a player can make and which not.
     *
     * example:
     * if you want to know if player 2 can make a legal move on [3,4]:
     * isLegal(3, 4, 2).
     *
     *
     * @param q vertical coordinate which must be inside the hexagon
     * @param r horizontal coordinate which must be inside the hexagon
     * @param playerColor identification of the player, which must be > 0
     */
    public boolean isLegal(int q, int r, int playerColor);

    /**
     * this will only change whenever the current player makes a legal move.
     *
     * @return identification of the player
     */
    public int getPlayerTurn();

    /**
     * @param playerColor identification of the player, which must be > 0
     * @return the total amount of groups a player has, which is positive number
     */
    public int getTotalGroups(int playerColor);

    /**
     * @param playerColor identification of the player, which must be > 0
     * @return size of the largest group a player, which is positive number
     */
    public int getLargestGroupSize(int playerColor);
}
