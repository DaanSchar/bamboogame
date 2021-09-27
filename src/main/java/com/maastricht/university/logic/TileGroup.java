package com.maastricht.university.logic;

import java.util.LinkedList;
import java.util.List;

public class TileGroup {

    private List<LogicTile> members;
    private int groupID;

    private static int count = 0;

    public TileGroup(List<LogicTile> members) {
        this.members = members;

        groupID = count++;
    }

    public TileGroup(LogicTile member) {
        this.members = new LinkedList<>();
        members.add(member);

        groupID = count++;
    }

    /**
     * Merges groups together by adding the other groups into this one
     * @param groups the TileGroups that will be merged into this group
     *
     */
    public void merge(List<TileGroup> groups) {
        for(int i=0; i<groups.size(); i++) {
            List<LogicTile> tiles = groups.get(i).getMembers();
            for(int j=0; j<tiles.size(); j++) {
                members.add(tiles.get(j));
                tiles.get(j).setTileGroup(this);
            }
            LogicTile tempTile = groups.get(i).getMembers().get(0);
            tempTile.getBoard().removeGroup(tempTile.getColour(), this);
        }
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
    public List<LogicTile> getMembers() {
        return new LinkedList<LogicTile>(members);
    }

    /**
     * Resets the list of LogicTiles in this group
     * @param list the new list of LogicTiles
     */
    public void setMembers(List<LogicTile> list) {
        members = list;
    }

    /**
     * add a LogicTile to the group
     * @param tile the tile that will be added to this group
     */
    public void addMember(LogicTile tile) {
        members.add(tile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileGroup group = (TileGroup) o;
        return groupID == group.groupID;
    }
}