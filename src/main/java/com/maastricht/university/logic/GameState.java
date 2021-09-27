package com.maastricht.university.logic;

import com.maastricht.university.frontend.Tile;

import java.util.LinkedList;
import java.util.List;

public class GameState {

    private Board board;

    /**
     * construct the gamestate
     * @param boardSize is the size of the board we are using
     * @param numberOfPlayers number of players that the user typed
     * @throws Exception
     */
    public GameState(final int boardSize, final int numberOfPlayers) throws Exception {
        this.board = new Board(boardSize, numberOfPlayers);

    }

    /**
     * Make sure that you get all the tiles from frontend
     * @param x coordinate
     * @param y coordinate
     * @param c color
     * @throws Exception
     */
    public void move(int x, int y, int c) throws Exception {
        board.move(x, y, c);
    }

    /**
     *
     * @return player turn
     */
    public int getPlayerTurn() {
        return board.getPlayerTurn();
    }

    /**
     * method that check if the new player move is legal
     * @param x coordinate
     * @param y coordinate
     * @param c colour
     * @return true or false depending on is legal move
     */
    public boolean isLegal(int q, int r, int playerColor) {
        LogicTile tile = board.getHexagon().get(q, r);

        if (tile.getColour() != 0)
            return false;

        if (!neighborsAreInGroup(tile))
            return true;

        if (getNewGroupSize(tile) > getMaxGroupSize(playerColor))
            return false;

        return true;
    }

    private boolean neighborsAreInGroup(LogicTile tile) {
        List<LogicTile> neighbors = getNeighbors(tile);

        for (LogicTile neighbor : neighbors)
            if (getGroup(neighbor) != null)
                return true;

        return false;
    }

    /**
     * a neighbor is defined of a tile is defined by its location in the hexagon structure.
     * if a tile is adjacent to our tile, it is considered a neighbor.
     * @param tile
     * @return list of adjacent tiles
     */
    private List<LogicTile> getNeighbors(LogicTile tile) {
        return board.getHexagon().getNeighbours(tile.getQ(), tile.getR());
    }

    /**
     * determines what the maximum total of tiles may be contained by one group.
     * this is the amount of groups the player has.
     * @param playerColor
     * @return maximum allowed size for a group
     */
    private int getMaxGroupSize(int playerColor) {
        //TODO: this implementation needs to be changed, as it needs an update before
        // it can determine the new max allowed size
        return board.getGroups(playerColor).size();
    }

    /**
     * determines how large a group will be if tile were to be colored.
     * that means that there is no new group created.
     * @return int size of group
     */
    private int getNewGroupSize(LogicTile tile) {
        List<LogicTile> neighbors = getNeighbors(tile);
        int size = 0;

        for (LogicTile neighbour : neighbors)
            if (getGroup(neighbour) != null)
                size += getGroup(neighbour).getGroupSize();

        return size;
    }

    /**
     *
     * @param tile the tile we want to receive its current group from
     * @return TileGroup which tile is located in
     */
    private TileGroup getGroup(LogicTile tile) {
        List<TileGroup> groups =  board.getGroups(tile.getColour());

        for (TileGroup group : groups)
            for (LogicTile groupTile : group.getGroupMembers())
                if (groupTile.equals(tile))
                    return group;

        return null;
    }
}