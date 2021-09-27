package com.maastricht.university.logic;
import java.util.LinkedList;
import java.util.List;

public class Board {

    // radius of the hexagon
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

    private void initGroups() {
        groups = new LinkedList[2];

        for(int i=0; i<numberOfPlayers; i++)
            groups[i] = new LinkedList<TileGroup>();
    }

    private void initBoard() {
        tileMap = new Hexagon<>(boardSize);

        for (int q = 0; q < tileMap.size(); q++)
            for (int r = 0; r < tileMap.size(); r++)
                tileMap.insert(q, r, new LogicTile(this, q, r));

        for (int q = 0; q < tileMap.size(); q++)
            for (int r = 0; r < tileMap.size(); r++)
                SetNeighboursOfTile(q, r);
    }

    private void SetNeighboursOfTile(int q, int r) {
        List<LogicTile> neighbours = tileMap.getNeighbours(q, r);

        for (LogicTile neighbour : neighbours) {
            LogicTile tile = tileMap.get(q, r);

            if (tile != null)
                tile.addNeighbour(neighbour);
        }
    }

    public IHexagon<LogicTile> getHexagon() {
        return this.tileMap;
    }

    /**
     * check if a tile can be coloured
     * @param x coordinate
     * @param y coordinate
     * @param playerColor colour that can be used to colour the tile
     * @throws Exception if is not that players turn or if the tile has been coloured by an illegal colour
     */
    public void move(int x, int y, int playerColor) throws Exception {
        //check if a legal colour
        if(playerColor<=0 || playerColor> numberOfPlayers) {
            throw new Exception("illegal Colour");
        }

        //check if that colours turn
        if(playerTurn != playerColor) {
            throw new Exception("Not that players turn");
        }

        //check if that tile can be coloured by that colour
        LogicTile tile = tileMap.get(x,y);
        boolean canColour = false;


        canColour= tile.isLegal(playerColor);

        //if it can't be coloured, throw an exception
        if(canColour) {
            tile.setColour(playerColor);
            if(playerTurn>= numberOfPlayers)
            {
                playerTurn=1;
            }
            else
            {
                playerTurn++;
            }
        }
        else {
            throw new Exception("illegal move");
        }
    }

    /**
     * add group to a tile
     * @param c colour
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
     * check if the next move is legal
     * @param x coordinate
     * @param y coordinate
     * @param playerColor colour
     * @return true or false if the tile can be coloured by that colour or not
     */
    public boolean isLegal(int x, int y, int playerColor)
    {
        LogicTile t = tileMap.get(x, y);
        return t.isLegal(playerColor);
    }

    /**
     * if there is a tile that link two different group
     * @param playerColor
     * @return it returns the size of the group of the previous move
     */
    public int getNumberOfGroups(int playerColor) {return groups[playerColor-1].size();}

    /**
     *
     * @param playerColor colour
     * @return the group of the previous move
     */
    public List<TileGroup> getGroups(int playerColor) {return groups[playerColor-1];}

    /**
     *
     * @return the playerTurn value
     */
    public int getPlayerTurn() { return playerTurn; }

    /**
     *
     * @param playerColor takes this variable to assign it to playerTurn
     */
    public void setPlayerTurn(int playerColor) { this.playerTurn = playerColor; }

}