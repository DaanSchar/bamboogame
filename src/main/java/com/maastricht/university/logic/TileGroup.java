package com.maastricht.university.logic;

import java.util.LinkedList;
import java.util.List;

public class TileGroup {
    private List<LogicTile> groupMembers;

    private int groupID;
    private static int count = 0;

    /**
     * Constructs a TileGroup and adds it's members to it
     * @param members The tiles of this group
     */
    public TileGroup(List<LogicTile> members) {
        groupMembers = members;
        for(int i=0; i<groupMembers.size(); i++) {
            groupMembers.get(i).setTileGroup(this);
        }
        groupID = count;
        count++;

        if(members.size() != 0) {
            members.get(0).getBoard().addGroup(members.get(0).getColour(), this); // TODO: dont do this, as this type of assignment can lead to spaghetti code.
        }
    }

    /**
     * Returns the number of tiles in this group
     * @return the number of tiles in this group
     */
    public int getGroupSize() {
        return groupMembers.size();
    }

    /**
     * Returns the ID of the group
     * @return the ID of the group
     */
    public int getGroupID() {
        return groupID;
    }

    /**
     * Returns all the LogicTiles in this group
     * @return all the LogicTiles in this group
     */
    public List<LogicTile> getGroupMembers() {
        return new LinkedList<LogicTile>(groupMembers);
    }

    /**
     * Resets the list of LogicTiles in this group
     * @param list the new list of LogicTiles
     */
    public void setGroupMembers(List<LogicTile> list) {
        groupMembers = list;
    }

    /**
     * add a LogicTile to the group
     * @param tile the tile that will be added to this group
     */
    public void addGroupMember(LogicTile tile) {
        groupMembers.add(tile);
        tile.setTileGroup(this);
    }

    /**
     * Merges groups together by adding the other groups into this one
     * @param groups the TileGroups that will be merged into this group
     *
     */
    public void mergeGroups(List<TileGroup> groups) {
        for(int i=0; i<groups.size(); i++) {
            List<LogicTile> tiles = groups.get(i).getGroupMembers();
            for(int j=0; j<tiles.size(); j++) {
                groupMembers.add(tiles.get(j));
                tiles.get(j).setTileGroup(this);
            }
            LogicTile tempTile = groups.get(i).getGroupMembers().get(0);
            tempTile.getBoard().removeGroup(tempTile.getColour(), this);
        }
    }
}