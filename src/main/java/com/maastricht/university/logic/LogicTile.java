package com.maastricht.university.logic;

import java.util.LinkedList;
import java.util.List;

public class LogicTile {
    private Board board;

    private TileGroup tileGroup;

    private int colour; //0 for no colour, 1 for red, 2 for blue
    private boolean legalForRed;
    private boolean legalForBlue;

    private List<LogicTile> neighbours = new LinkedList<LogicTile>();

    public LogicTile(Board b) {
        board = b;
        colour = 0;
        legalForBlue = true;
        legalForRed = true;
    }

    public void setTileGroup(TileGroup group) {
        tileGroup = group;
    }

    public TileGroup getTileGroup() {
        return tileGroup;
    }

    public int getColour() {
        return colour;
    }

    public void addNeighbour(LogicTile neighbour) {
        if (!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }
    }

    public List<LogicTile> getNeighbours() {
        return new LinkedList<LogicTile>(neighbours);
    }


    public void setColour(int c) throws Exception {
        if (c == 1 && isLegalForRed()) {
            colour = c;
        } else if (c == 2 && isLegalForBlue()) {
            colour = c;
        } else {
            throw new Exception("illegal Colouring");
        }

        updateSurroundingTilesAndBoard(c);
    }

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


        //TODO: communicate with board how many groups there are (part of implementing list of groups in board)
        /*
        This doesn't work when merging group(s)
        if(c==1) {
            board.setNumberOfGroupsRed(board.getNumberOfGroupsRed()+1);
        }
        else if(c==2) {
            board.setNumberOfGroupsBlue(board.getNumberOfGroupsBlue()+1);
        }
         */
    }

    public List<LogicTile> getSurroundedTilesFromDifferentGroups (int c){
        List<LogicTile> neighbours = getNeighbours();
        List<LogicTile> tilesFromDifferentGroups = new LinkedList<LogicTile>();
        for (int i = 0; i < neighbours.size(); i++) {
            LogicTile tempTile = neighbours.get(i);
            //if they are coloured blue
            if (tempTile.getColour() == c) {
                //check if one of that group is already added to the list
                //only add if that is not the case (don't want duplicated groups)
                boolean duplicate = false;
                for (int j = 0; j < tilesFromDifferentGroups.size(); j++) {
                    if (tempTile.getTileGroup().getGroupID() == tilesFromDifferentGroups.get(j).getTileGroup().getGroupID()) {
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

    public List<LogicTile> getSurroundedTilesFromGroups(int c) {
        List<LogicTile> neighbours = getNeighbours();
        List<LogicTile> tilesConnectedAndColoured = new LinkedList<LogicTile>();
        for(int i=0; i<neighbours.size(); i++) {
            LogicTile tempTile = neighbours.get(i);
            //if they are coloured blue, add it too the list
            if(tempTile.getColour()==c) {
                tilesConnectedAndColoured.add(tempTile);
            }
        }
        return tilesConnectedAndColoured;
    }

    //TODO: check all other groups too for size in case of less groups instead of only the newly formed group
    //TODO: remove duplicate code from the isLegal methods
    public boolean isLegalForRed() {
        int numberOfGroups = board.getNumberOfGroupsRed();

        //if the tile is already coloured, return false
        if(getColour()!=0)
        {
            return false;
        }

        //make list of all groups connected to tile
        List<LogicTile> tilesFromDifferentGroups = getSurroundedTilesFromDifferentGroups(1);
        List<LogicTile> tilesConnectedAndColoured = getSurroundedTilesFromGroups(1);

        //if no groups connected to that tile, return true
        if(tilesFromDifferentGroups.size()==0) {
            return true;
        }

        //if the the new groupsize (if coloured) will be too big, it's not possible
        int groupSizesFromAllNeighbours = 0;
        for(int i=0; i< tilesFromDifferentGroups.size(); i++) {
            groupSizesFromAllNeighbours += tilesFromDifferentGroups.get(i).getTileGroup().getGroupSize();
        }
        if(groupSizesFromAllNeighbours+1 > numberOfGroups + 1 - (tilesFromDifferentGroups.size()))
        {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isLegalForBlue() {
        int numberOfGroups = board.getNumberOfGroupsBlue();

        //if the tile is already coloured, return false
        if(getColour()!=0)
        {
            return false;
        }

        //make list of all groups connected to tile
        List<LogicTile> tilesFromDifferentGroups = getSurroundedTilesFromDifferentGroups(1);
        List<LogicTile> tilesConnectedAndColoured = getSurroundedTilesFromGroups(1);

        //if no groups connected to that tile, return true
        if(tilesFromDifferentGroups.size()==0) {
            return true;
        }

        //if the the new groupsize (if coloured) will be too big, it's not possible
        int groupSizesFromAllNeighbours = 0;
        for(int i=0; i< tilesFromDifferentGroups.size(); i++) {
            groupSizesFromAllNeighbours += tilesFromDifferentGroups.get(i).getTileGroup().getGroupSize();
        }
        if(groupSizesFromAllNeighbours+1 > numberOfGroups + 1 - (tilesFromDifferentGroups.size()))
        {
            return false;
        }
        else {
            return true;
        }
    }
}