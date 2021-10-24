package com.maastricht.university.logic.game.game;

import com.maastricht.university.logic.game.components.Board;
import com.maastricht.university.logic.game.components.LogicTile;
import com.maastricht.university.logic.game.components.TileGroup;

import java.util.List;

/**
 *  This class determines if a move, drawing a certain tile by a color,
 *  is allowed or not.
 */
public class GameRule {

    private Board board;

    public GameRule (Board board){
        this.board = board;
    }

    /**
     * a player must have enough groups to make a legal move.
     * a move must result in still having enough groups to justify the move's legality.
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
        return board.getNeighboringGroups(tile.getQ(), tile.getR(), playerColor);
    }

}
