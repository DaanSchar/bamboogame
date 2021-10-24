package com.maastricht.university.logic.game.components;

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
     * Returns all the LogicTiles in this group
     * @return all the LogicTiles in this group
     */
    public List<LogicTile> getMembers() {
        return new LinkedList<>(members);
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

    public void setGroupID(int id) {
        groupID = id;
    }

    public TileGroup cloneFromTileMap(Hexagon<LogicTile> tileMap) {
        TileGroup cloneTileGroup = new TileGroup(this.playerColor);

        for(int i=0; i<members.size(); i++) {
            LogicTile oldTile = members.get(i);
            LogicTile newTile = tileMap.get(oldTile.getQ(), oldTile.getR());
            cloneTileGroup.addMember(newTile);
        }
        cloneTileGroup.setGroupID(this.groupID);
        return cloneTileGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileGroup group = (TileGroup) o;
        return groupID == group.groupID;
    }
}