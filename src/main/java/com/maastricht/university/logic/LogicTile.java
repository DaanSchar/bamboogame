package com.maastricht.university.logic;

import java.util.LinkedList;
import java.util.List;

public class LogicTile {
    private final Board board; //TODO: Why does a tile need a reference to the board its in?

    private TileGroup tileGroup; //TODO: why do we need a reference to the group its in.

    private int colour; //0 for no colour, 1 for red, 2 for blue

    private List<LogicTile> neighbours = new LinkedList<LogicTile>();

    /**
     * Constructs an empty Tile
     * @param b the board that contains this tile
     */
    public LogicTile(Board b) {
        board = b;
        colour = 0;
    }

    /**
     * Sets the reference to the group this tile is part of
     * @param group the group of tiles this tile is part of
     */
    public void setTileGroup(TileGroup group) {
        tileGroup = group;
    }

    /**
     * Returns the group of tiles this tile is part of
     * @return the group of tiles this tile is part of
     */
    public TileGroup getTileGroup() {
        return tileGroup;
    }

    /**
     * Returns the board this tiles is located on
     * @return the board this tiles is located on
     */
    public Board getBoard() {return board;}

    /**
     * Returns the colour this tile is coloured with
     * @return the colour of this tile (postive int, 0 if not coloured)
     */
    public int getColour() {
        return colour;
    }

    /**
     * Set another tile as this tile's neigbour
     * @param neighbour a neighbour of this tile
     */
    public void addNeighbour(LogicTile neighbour) {
        if (!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }
    }

    /**
     * Returns all the neighbours of this tile
     * @return all the neighbours of this tile
     */
    public List<LogicTile> getNeighbours() {
        return new LinkedList<LogicTile>(neighbours);
    }

    /**
     * Set the colour of the tile
     * @param c the new colour of this tile
     */
    public void setColour(int c) throws Exception {
        if (c != 0  && isLegal(c)) {
            colour = c;
        }
        else {
            throw new Exception("illegal Colouring");
        }

        updateSurroundingTilesAndBoard(c);
    }

    /**
     * Updates all necessary information about the board when the tile gets coloured
     * @param c the new colour of this tile
     */
    private void updateSurroundingTilesAndBoard(int c) {
        List<LogicTile> tilesFromDifferentGroups = getSurroundedTilesFromDifferentGroups(c);     // not sure how to resolve this method call without reordering the methods

        //check if group needs to be changed. if new group, make a new TileGroup
        if (tilesFromDifferentGroups.size() == 0) {
            List<LogicTile> members = new LinkedList<>();
            members.add(this);
            tileGroup = new TileGroup(members);
        }

        //if only one group, use that TileGroup for this tile too
        if (tilesFromDifferentGroups.size() == 1) {
            tilesFromDifferentGroups.get(0).getTileGroup().addGroupMember(this);
        }

        //if multiple groups, merge those groups together and add the current tile into it
        List<TileGroup> groupsToMerge = new LinkedList<>();
        for(int i=1; i<tilesFromDifferentGroups.size(); i++) {
            groupsToMerge.add(tilesFromDifferentGroups.get(i).getTileGroup());
        }
        tilesFromDifferentGroups.get(0).getTileGroup().mergeGroups(groupsToMerge);
        tilesFromDifferentGroups.get(0).getTileGroup().addGroupMember(this);
    }

    /**
     * Gets a list of tiles that contains a tile from every group with a certain colour that this tile is neighbours with
     * @param c the colour the groups can be
     * @return a list of tiles that contains a tile from every group with a certain colour that this tile is neighbours with
     */
    public List<LogicTile> getSurroundedTilesFromDifferentGroups (int c){
        List<LogicTile> neighbours = getNeighbours();
        List<LogicTile> tilesFromDifferentGroups = new LinkedList<>();
        for(LogicTile tempTile : neighbours) {
            //if they are coloured blue
            if (tempTile.getColour() == c) {
                //check if one of that group is already added to the list
                //only add if that is not the case (don't want duplicated groups)
                boolean duplicate = false;
                for (LogicTile tilesFromDifferentGroup : tilesFromDifferentGroups) {
                    if (tempTile.getTileGroup().getGroupID() == tilesFromDifferentGroup.getTileGroup().getGroupID()) {
                        duplicate = true;
                    }
                }
                if (!duplicate) {
                    tilesFromDifferentGroups.add(tempTile);
                }
            }
        }
        return tilesFromDifferentGroups;
    }

    /**
     * Gets a list of tiles that contains all the neighbouring tiles that are coloured
     * @param c the colour the groups can be
     * @return a list of tiles that contains all the neighbouring tiles that are coloured
     */
    public List<LogicTile> getSurroundedTilesFromGroups(int c) {
        List<LogicTile> neighbours = getNeighbours();
        List<LogicTile> tilesConnectedAndColoured = new LinkedList<LogicTile>();
        for (LogicTile tempTile : neighbours) {
            //if they are coloured blue, add it too the list
            if (tempTile.getColour() == c) {
                tilesConnectedAndColoured.add(tempTile);
            }
        }
        return tilesConnectedAndColoured;
    }


    /**
     * Checks whether this specific tile can legally be coloured by a specific colour
     * @param c the colour we want to check the legality of
     * @return whether this specific tile can legally be coloured by c
     */
    public boolean isLegal(int c)
    {
        int numberOfGroups = board.getNumberOfGroups(c);

        //if the tile is already coloured, return false
        if(colour!=0)
        {
            return false;
        }

        //make list of all groups connected to tile
        List<LogicTile> tilesFromDifferentGroups = getSurroundedTilesFromDifferentGroups(c);

        //if no groups connected to that tile, return true
        if(tilesFromDifferentGroups.size()==0) {
            return true;
        }

        //if the the new groupsize (if coloured) will be too big, it's not possible
        int groupSizesFromAllNeighbours = 0;
        for (LogicTile tilesFromDifferentGroup : tilesFromDifferentGroups) {
            groupSizesFromAllNeighbours += tilesFromDifferentGroup.getTileGroup().getGroupSize();
        }
        if(groupSizesFromAllNeighbours+1 > numberOfGroups + 1 - (tilesFromDifferentGroups.size()))
        {
            return false;
        }

        //check if any of the existing groups is too big (since number of groups becomes smaller)
        List<TileGroup> groups = board.getGroups(c);
        for (TileGroup group : groups) {
            if (group.getGroupSize() > numberOfGroups + 1 - (tilesFromDifferentGroups.size())) {
                return false;
            }
        }
        //if that is not the case, return true
        return true;
    }
}