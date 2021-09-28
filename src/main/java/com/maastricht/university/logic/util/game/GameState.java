package com.maastricht.university.logic.util.game;

import com.maastricht.university.logic.util.exceptions.IllegalMoveException;
import com.maastricht.university.logic.util.exceptions.OutsideHexagonException;
import com.maastricht.university.logic.util.interfaces.IGameState;

import java.util.LinkedList;
import java.util.List;

public class GameState implements IGameState {

    private Board board;
    private int playerTurn;
    private int numberOfPlayers;

    /**
     * construct the gamestate
     * @param boardSize is the size of the board we are using, defined as the diameter of the hexagon.
     * @param numberOfPlayers number of players that the user typed
     */
    public GameState(final int boardSize, final int numberOfPlayers) {
        this.board = new Board(boardSize, numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
        playerTurn = 1;
    }

    /**
     * performs a move if it is legal
     */
    public void move(int q, int r, int playerColor) {
        try {
            findIllegalException(q,r,playerColor);

            board.move(q, r, playerColor);
            playerTurn = getNextPlayer();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("current player is still " + playerTurn);
        }
    }

    /**
     * method that check if the new player move is legal
     * @return true or false depending on is legal move
     */
    public boolean isLegal(int q, int r, int playerColor) {
        LogicTile tile = board.getTileMap().get(q, r);

        if (tile.getPlayerColor() != 0)
            return false;


        if (getNeighboringGroups(tile, playerColor).size() == 0)
            return true;

        if (getNewGroupSize(tile, playerColor) > getNewTotalGroups(tile, playerColor))
            return false;

        return true;
    }


    public Board getBoard() {
        return board;
    }

    /**
     *
     * @return an int representing the player who's turn it is, which must be > 0
     */
    public int getPlayerTurn() { return playerTurn; }

    /**
     * takes an int representing the player, which must be > 0,
     * setting who's players turn it is to that player.
     * @param playerColor
     */
    public void setPlayerTurn(int playerColor) { this.playerTurn = playerColor; }










    /**
     * returns the size of the group this tile will reside in once
     * it gets colored
     * @param tile
     * @return size of the to be created group
     */
    private int getNewGroupSize(LogicTile tile, int playerColor) {
        List<TileGroup> groups = getNeighboringGroups(tile, playerColor);
        int size = 0;

        for (TileGroup group : groups)
            size += group.getMembers().size();

        return size + 1;
    }

    /**
     * when we color a tile which is neighboring one or multiple groups,
     * these groups will merge, decreasing the amount of total groups.
     * @return total groups if tile were to be colored
     */
    private int getNewTotalGroups(LogicTile tile, int playerColor) {
        int totalGroups = board.getGroups(playerColor).size();
        int totalNeighboringGroups = getNeighboringGroups(tile, playerColor).size();
        return totalGroups+1 - (totalNeighboringGroups);
    }

    /**
     * since getting the group of each neighbor results in having
     * duplicate groups if 2 neighbors are in the same group, we
     * have to filter this this list for duplicates.
     * @param tile
     * @return list of unique groups of the neighbors of tile
     */
    private List<TileGroup> getNeighboringGroups(LogicTile tile, int playerColor) {
        List<LogicTile> neighbors = getNeighbors(tile);
        List<TileGroup> groups = new LinkedList<>();

        for (LogicTile neighbor : neighbors)
            if (board.getGroup(neighbor) != null)
                if (board.getGroup(neighbor).getPlayerColor() == playerColor)
                    groups.add(board.getGroup(neighbor));


        removeDuplicateGroups(groups);
        return groups;
    }

    private void removeDuplicateGroups(List<TileGroup> groups) {
        for (int i = 0; i < groups.size(); i++)
            for (int j = i + 1; j < groups.size(); j++)
                if (groups.get(i).equals(groups.get(j)))
                    groups.remove(i);
    }

    /**
     * a neighbor of a tile is defined by its location in the hexagon structure.
     * if a tile is adjacent to our tile, it is considered a neighbor.
     * @param tile
     * @return list of adjacent tiles
     */
    private List<LogicTile> getNeighbors(LogicTile tile) {
        return board.getTileMap().getNeighbours(tile.getQ(), tile.getR());
    }

    /**
     * throws the exception that need to be caught inside move()
     */
    private void findIllegalException(int q, int r, int playerColor) throws Exception {
        if (board.getTileMap().get(q, r) == null)
            throw new OutsideHexagonException("Coordinates are outside tilemap, returned null");
        if (!isLegal(q, r, playerColor))
            throw new IllegalMoveException("Move is Illegal");
        if (playerColor != playerTurn)
            throw new IllegalMoveException("It is not their turn.");
        if (playerColor > numberOfPlayers)
            throw new IllegalMoveException("there are only " + numberOfPlayers + " players.");
        if (playerColor < 1)
            throw new IllegalMoveException("player must be bigger than 0");
    }

    private int getNextPlayer() {
        int nextPlayer = playerTurn+1;

        if (nextPlayer > numberOfPlayers)
            return 1;
        return nextPlayer;
    }


}