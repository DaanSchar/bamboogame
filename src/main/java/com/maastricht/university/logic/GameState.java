package com.maastricht.university.logic;

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

        if (getNeighboringGroups(tile).size() == 0)
            return true;

        if (getNewGroupSize(tile) > getNewTotalGroups(tile))
            return false;

        return true;
    }

    /**
     * since getting the group of each neighbor results in having
     * duplicate groups if 2 neighbors are in the same group, we
     * have to filter this this list for duplicates.
     * @param tile
     * @return list of unique groups of the neighbors of tile
     */
    private List<TileGroup> getNeighboringGroups(LogicTile tile) {
        List<LogicTile> neighbors = getNeighbors(tile);
        List<TileGroup> groups = new LinkedList<>();

        for (LogicTile neighbor : neighbors)
            if (getGroup(neighbor) != null)
                groups.add(getGroup(neighbor));

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
        return board.getHexagon().getNeighbours(tile.getQ(), tile.getR());
    }

    /**
     * returns the size of the group this tile will reside in once
     * it gets colored
     * @param tile
     * @return size of the to be created group
     */
    private int getNewGroupSize(LogicTile tile) {
        List<TileGroup> groups = getNeighboringGroups(tile);
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
    private int getNewTotalGroups(LogicTile tile) {
        int totalNeighboringGroups = board.getGroups(tile.getColour()).size();
        int totalGroups = getNeighboringGroups(tile).size();
        return totalGroups - (totalNeighboringGroups + 1);
    }

    /**
     * @param tile the tile we want to receive its current group from
     * @return TileGroup which tile is located in
     */
    private TileGroup getGroup(LogicTile tile) {
        List<TileGroup> groups =  board.getGroups(tile.getColour());

        for (TileGroup group : groups)
            for (LogicTile groupTile : group.getMembers())
                if (groupTile.equals(tile))
                    return group;

        return null;
    }
}