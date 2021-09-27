package com.maastricht.university.logic;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private int boardSize;

    private int playerTurn;
    private int numberOfPlayers;

    private List<TileGroup>[] groups;
    private Hexagon<LogicTile> tileMap;

    /**
     * Constructs the board
     * @param boardSize is the size of our board
     * @param numberOfPlayers is the number of players that the user decided
     */
    public Board(final int boardSize, final int numberOfPlayers) throws Exception {
        this.boardSize = boardSize;
        this.numberOfPlayers = numberOfPlayers;

        initGroups();
        initBoard();
    }


    /**
     * check if a tile can be coloured
     * @param x coordinate
     * @param y coordinate
     * @param playerColor colour that can be used to colour the tile
     * @throws Exception if is not that players turn or if the tile has been coloured by an illegal colour
     */
    public void move(int x, int y, int playerColor) throws Exception {
        //TODO: refactor this to fit the current code.
//        //check if a legal colour
//        if(playerColor<=0 || playerColor> numberOfPlayers) {
//            throw new Exception("illegal Colour");
//        }
//
//        //check if that colours turn
//        if(playerTurn != playerColor) {
//            throw new Exception("Not that players turn");
//        }
//
//        //check if that tile can be coloured by that colour
//        LogicTile tile = tileMap.get(x,y);
//        boolean canColour = false;
//
//
//        canColour= tile.isLegal(playerColor);
//
//        //if it can't be coloured, throw an exception
//        if(canColour) {
//            tile.setColour(playerColor);
//            if(playerTurn>= numberOfPlayers)
//            {
//                playerTurn=1;
//            }
//            else
//            {
//                playerTurn++;
//            }
//        }
//        else {
//            throw new Exception("illegal move");
//        }
    }

    /**
     * add group to a tile
     * @param playerColor colour
     * @param group new group added
     */
    public void addGroup(int playerColor, TileGroup group) {
        groups[playerColor-1].add(group);
    }

    /**
     * remove group from a tile
     * @param playerColor colour
     * @param group removed group
     */
    public void removeGroup(int playerColor, TileGroup group) {
        groups[playerColor-1].remove(group);
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
    public List<TileGroup> getGroups(int playerColor) {return groups[playerColor-1];}

    /**
     *
     * @return an int representing the player who's turn it is, which must be > 0
     */
    public int getPlayerTurn() { return playerTurn; }

    /**
     * takes an int representing the player, which must be > 0,
     * setting who's players turn it is to that player.
     * @param playerColor
     */
    public void setPlayerTurn(int playerColor) { this.playerTurn = playerColor; }

    public IHexagon<LogicTile> getHexagon() {
        return this.tileMap;
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
        TileGroup newGroup = new TileGroup();

        for (TileGroup group : groups)
            for(LogicTile tile : group.getMembers())
                newGroup.addMember(tile);

        for (TileGroup group : groups)
            for(LogicTile tile : group.getMembers())
                removeGroup(tile.getPlayerColor(), group);

        addGroup(newGroup.getMembers().get(0).getPlayerColor(), newGroup);



    }

    private void initGroups() {
        groups = new LinkedList[2];

        for(int i=0; i<numberOfPlayers; i++)
            groups[i] = new LinkedList<TileGroup>();
    }

    private void initBoard() {
        tileMap = new Hexagon<>(boardSize);

        for (int q = 0; q < tileMap.size(); q++)
            for (int r = 0; r < tileMap.size(); r++)
                tileMap.insert(q, r, new LogicTile( q, r));
    }


}