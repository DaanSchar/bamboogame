package com.maastricht.university.logic;

import java.util.LinkedList;
import java.util.List;

public class TileGroup {

    private List<LogicTile> members;
    private int groupID;
    private int playerColor;

    private static int count = 0;

    public TileGroup(LogicTile member) {
        this.playerColor = member.getPlayerColor();
        this.members = new LinkedList<>();
        members.add(member);

        groupID = count++;
    }

    public TileGroup(int playerColor) {
        this.playerColor = playerColor;
        this.members = new LinkedList<>();
        groupID = count++;
    }

    public int getPlayerColor() {
        return this.playerColor;
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
     * @param members the new list of LogicTiles
     */
    public void setMembers(List<LogicTile> members) {
        try {
            if (!membersAreSameColor(members))
                throw new IllegalArgumentException("Members are not of the same PlayerColor");
            if (members.get(0).getPlayerColor() != playerColor)
                throw new IllegalArgumentException("Cant set tilelist as members of this group because the tile's playerColor do not match the groups playerColor");

            this.members = members;

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * add a LogicTile to the group
     * @param tile the tile that will be added to this group
     */
    public void addMember(LogicTile tile) {
        try {
            if (tile.getPlayerColor() != playerColor)
                throw new IllegalArgumentException("Cant add tile to a group because the tile's playerColor doesn't match the groups playerColor");

            members.add(tile);

        } catch (Exception e) {
            System.out.println(e);
        }
    }












    /**
     * checks if all members in a list are of the same playerColor
     */
    private boolean membersAreSameColor(List<LogicTile> members) {
        for (LogicTile member : members)
            if (member.getPlayerColor() != playerColor)
                return false;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileGroup group = (TileGroup) o;
        return groupID == group.groupID;
    }
}