package com.maastricht.university.logic.game;
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
        initTilemap();
    }

    /**
     * colors the tile at coordinate q, r.
     * cannot color tile if it's playerColor > 0
     * @param playerColor colour that can be used to colour the tile
     */
    public void move(int q, int r, int playerColor) {
        try {
            if (playerColor > numberOfPlayers)
                throw new IllegalArgumentException("playerColor is not a legal color");
            tileMap.get(q, r).setPlayerColour(playerColor);
            addGroup(new TileGroup(tileMap.get(q, r)));
            mergeNeighboringGroups(q, r);

        } catch (Exception e) {
            System.out.println(e);
        }
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

    /**
     * since getting the group of each neighbor results in having
     * duplicate groups if 2 neighbors are in the same group, we
     * have to filter this list for duplicates.
     * @return list of unique groups of the neighbors with the playerColor of tile
     */
    public List<TileGroup> getNeighboringGroups(int q, int r, int playerColor) {
        LogicTile tile = tileMap.get(q, r);
        List<LogicTile> neighbors = tileMap.getNeighbours(q, r);
        List<TileGroup> groups = new LinkedList<>();

        if (neighbors.size() == 0)
            return groups;

        for (LogicTile neighbor : neighbors)
            if (getGroup(neighbor) != null)
                if (getGroup(neighbor).getPlayerColor() == playerColor)
                    groups.add(getGroup(neighbor));

        return removeDuplicateGroups(groups);
    }

    public void setTileMap(Hexagon<LogicTile> newTileMap) {
        this.tileMap = newTileMap;
    }


    @Override
    public Board clone() {
        Board cloneBoard = new Board(boardSize, numberOfPlayers);
        Hexagon<LogicTile> cloneTileMap = (Hexagon<LogicTile>) this.tileMap.clone();
        cloneBoard.setTileMap(cloneTileMap);

        for(int i=0; i<groups.length; i++) {
            for(int j=0; j<groups[i].size(); j++) {
                TileGroup cloneGroup = groups[i].get(j).cloneFromTileMap(cloneTileMap);
                cloneBoard.addGroup(cloneGroup);
            }
        }
        return cloneBoard;
    }














    private void initGroups() {
        groups = new LinkedList[numberOfPlayers];

        for(int i=0; i<numberOfPlayers; i++)
            groups[i] = new LinkedList<TileGroup>();
    }

    private void initTilemap() {
        tileMap = new Hexagon<>(boardSize);

        for (int q = 0; q < tileMap.size(); q++)
            for (int r = 0; r < tileMap.size(); r++)
                tileMap.insert(q, r, new LogicTile( q, r));
    }

    private List<TileGroup> removeDuplicateGroups(List<TileGroup> groups) {
        for (int i = 0; i < groups.size(); i++)
            for (int j = i + 1; j < groups.size(); j++)
                if (groups.get(i).equals(groups.get(j)))
                    groups.remove(i);

        return groups;
    }

    /**
     * checks if there are any groups that neighbor each other and
     * merges them to one group
     */
    private void mergeNeighboringGroups(int q, int r) {
        if (tileMap.get(q,r) == null || tileMap.get(q,r).getPlayerColor()==0)
            return;

        List<TileGroup> neighborGroup = getNeighboringGroups(q, r, tileMap.get(q, r).getPlayerColor());

        if (neighborGroup.size() > 0) {
            neighborGroup.add(getGroup(tileMap.get(q, r)));
            merge(neighborGroup);
        }
    }

}