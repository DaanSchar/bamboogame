package com.maastricht.university.logic;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private int playerTurn;

    private int numberOfPlayers;

    private List<TileGroup>[] groups;

//    private LogicTile[][][] board;
    private Hexagon<LogicTile> tileMap; // <--- replacement of LogicTile[][][] board

    // radius of the hexagon
    private int boardSize;

    /**
     * Constructs the board
     * @param boardSize is the size of our board
     * @param numberOfPlayers is the number of players that the user decided
     */

    public Board(int boardSize, int numberOfPlayers) throws Exception {
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

        for (int i = 0; i < tileMap.size(); i++)
            for (int j = 0; j < tileMap.size(); j++)
                tileMap.insert(i, j, new LogicTile(this));

        for (int i = 0; i < tileMap.size(); i++)
            for (int j = 0; j < tileMap.size(); j++)
                SetNeighboursOfTile(i, j);
    }

    private void SetNeighboursOfTile(int q, int r) {
        List<LogicTile> neighbours = tileMap.getNeighbours(q, r);

        for (LogicTile neighbour : neighbours) {
            LogicTile tile = tileMap.get(q, r);

            if (tile != null)
                tile.addNeighbour(neighbour);
        }
    }

    /**
     * check if a tile can be coloured
     * @param x coordinate
     * @param y coordinate
     * @param c colour that can be used to colour the tile
     * @throws Exception if is not that players turn or if the tile has been coloured by an illegal colour
     */

    public void move(int x, int y, int c) throws Exception {
        //check if a legal colour
        if(c<=0 || c> numberOfPlayers) {
            throw new Exception("illegal Colour");
        }

        //check if that colours turn
        if(playerTurn != c) {
            throw new Exception("Not that players turn");
        }

        //check if that tile can be coloured by that colour
        LogicTile tile = tileMap.get(x,y);
        boolean canColour = false;


        canColour= tile.isLegal(c);

        //if it can't be coloured, throw an exception
        if(canColour) {
            tile.setColour(c);
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
    public void addGroup(int c, TileGroup group) {
        groups[c-1].add(group);
    }

    /**
     * remove group from a tile
     * @param c colour
     * @param group removed group
     */
    public void removeGroup(int c, TileGroup group) {
        groups[c-1].remove(group);
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
     * @param c colour
     * @return true or false if the tile can be coloured by that colour or not
     */
    public boolean isLegal(int x, int y, int c)
    {
        LogicTile t = tileMap.get(x, y);
        return t.isLegal(c);
    }

    /**
     * check if the coordinates of the board are correct
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @return true or false if the board size is correct or not
     */
    public boolean isLegalcoordinates(int x, int y, int z)
    {
        if(x<0 || y<0 || z<0)
        {
            return false;
        }
        if(x>boardSize || y>boardSize || z>boardSize )
        {
            return false;
        }
        return true;
    }

    /**
     * if there is a tile that link two different group
     * @param c
     * @return it returns the size of the group of the previous move
     */
    public int getNumberOfGroups(int c) {return groups[c-1].size();}

    /**
     *
     * @param c colour
     * @return the group of the previous move
     */
    public List<TileGroup> getGroups(int c) {return groups[c-1];}

    /**
     *
     * @return the playerTurn value
     */
    public int getPlayerTurn() { return playerTurn; }

    /**
     *
     * @param pTurn takes this variable to assign it to playerTurn
     */
    public void setPlayerTurn(int pTurn) { this.playerTurn = pTurn; }

}