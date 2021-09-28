package com.maastricht.university.logic.util.game;
import com.maastricht.university.logic.util.interfaces.IHexagon;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private int boardSize;

    private int numberOfPlayers;

    private List<TileGroup>[] groups;
    private Hexagon<LogicTile> tileMap;

    /**
     * Constructs the board
     * @param boardSize is the size of our board
     * @param numberOfPlayers is the number of players that the user decided
     */
    public Board(final int boardSize, final int numberOfPlayers) {
        this.boardSize = boardSize;
        this.numberOfPlayers = numberOfPlayers;

        initGroups();
        initBoard();
    }

    /**
     * colors the tile at coordinate q, r.
     * cannot color tile if it's playerColor > 0
     * @param playerColor colour that can be used to colour the tile
     */
    public void move(int q, int r, int playerColor) {
        tileMap.get(q, r).setPlayerColour(playerColor);
        addGroup(new TileGroup(tileMap.get(q, r)));
        updateGroups();
    }

    /**
     * @param tile the tile we want to receive its current group from
     * @return TileGroup where tile is located in
     */
    public TileGroup getGroup(LogicTile tile) {
        if (tile.getPlayerColor() == 0)
            return null;

        List<TileGroup> groups = getGroups(tile.getPlayerColor());

        for (TileGroup group : groups)
            for (LogicTile groupTile : group.getMembers())
                if (groupTile.equals(tile))
                    return group;

        return null;
    }

    /**
     * add group to a tile
     * @param group new group added
     */
    public void addGroup(TileGroup group) {
        groups[group.getPlayerColor()-1].add(group);
    }

    /**
     * remove group from a tile
     * @param group removed group
     */
    public void removeGroup(TileGroup group) {
        groups[group.getPlayerColor()-1].remove(group);
    }

    /**
     *
     * @return the length of the board
     */
    public int getBoardSize() {return boardSize;}

    /**
     *
     * @param playerColor colour
     * @return the current groups which belong to the player of playerColor
     */
    public List<TileGroup> getGroups(int playerColor) {
        if (playerColor == 0)
            return new LinkedList<>();

        return groups[playerColor-1];
    }

    public IHexagon<LogicTile> getTileMap() {
        return this.tileMap;
    }

    /**
     * Merges groups together by adding the other groups into this one
     * @param groups the TileGroups that will be merged into this group
     *
     */
    public void merge(List<TileGroup> groups) {
        TileGroup newGroup = new TileGroup(groups.get(0).getPlayerColor());

        for (TileGroup group : groups) {
            for (LogicTile tile : group.getMembers())
                newGroup.addMember(tile);
            removeGroup(group);
        }

        addGroup(newGroup);
    }














    private void initGroups() {
        groups = new LinkedList[numberOfPlayers];

        for(int i=0; i<numberOfPlayers; i++)
            groups[i] = new LinkedList<TileGroup>();
    }

    private void initBoard() {
        tileMap = new Hexagon<>(boardSize);

        for (int q = 0; q < tileMap.size(); q++)
            for (int r = 0; r < tileMap.size(); r++)
                tileMap.insert(q, r, new LogicTile( q, r));
    }

    /**
     * since getting the group of each neighbor results in having
     * duplicate groups if 2 neighbors are in the same group, we
     * have to filter this this list for duplicates.
     * @return list of unique groups of the neighbors with the playerColor of tile
     */
    private List<TileGroup> getNeighboringGroups(int q, int r) {
        LogicTile tile = tileMap.get(q, r);
        List<LogicTile> neighbors = tileMap.getNeighbours(q, r);
        List<TileGroup> groups = new LinkedList<>();

        for (LogicTile neighbor : neighbors)
            if (getGroup(neighbor) != null)
                if (getGroup(neighbor).getPlayerColor() == tile.getPlayerColor())
                    groups.add(getGroup(neighbor));

        return removeDuplicateGroups(groups);
    }

    private List<TileGroup> removeDuplicateGroups(List<TileGroup> groups) {
        for (int i = 0; i < groups.size(); i++)
            for (int j = i + 1; j < groups.size(); j++)
                if (groups.get(i).equals(groups.get(j)))
                    groups.remove(i);

        return groups;
    }

    /**
     * checks all tiles for any necessary actions
     */
    private void updateGroups() {
        for (int q = 0; q < tileMap.size(); q++)
            for (int r = 0; r < tileMap.size(); r++)
                mergeNeighboringGroups(q, r);
    }

    /**
     * checks if there are any groups that neighbor each other and
     * merges them to one group
     */
    private void mergeNeighboringGroups(int q, int r) {

        if (tileMap.get(q,r) == null)
            return;

        List<TileGroup> neighborGroup = getNeighboringGroups(q, r);

        if (neighborGroup.size() > 0) {
            neighborGroup.add(getGroup(tileMap.get(q, r)));
            merge(neighborGroup);
        }
    }

}