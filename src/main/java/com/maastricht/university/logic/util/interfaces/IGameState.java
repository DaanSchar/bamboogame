package com.maastricht.university.logic.util.interfaces;

/**
 * this interface makes sure that the frontend wont have access to anything we dont want
 * the frontend to have access to. we do this by giving this interface methods for
 * everything that the frontend might need as primitive types, which are not mutable.
 */
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

    /**
     * return which player has colored this tile
     *
     * @param q vertical coordinate which must be inside the hexagon
     * @param r horizontal coordinate which must be inside the hexagon
     * @return identifcation of the player
     */
    public int getPlayerColorOfTile(int q, int r);

    /**
     * return the winner of the game
     *
     * @return identification of the player who has won, or 0 if no winner
     */
    public int winner();
}
