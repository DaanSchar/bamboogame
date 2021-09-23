package com.maastricht.university.logic;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private int playerTurn;

    private int numberOfPlayers;

    private List<TileGroup>[] groups;

    private LogicTile[][][] board;
    private int boardLength;

    /**
     * Constructs the board
     * @param boardSize is the size of our board
     * @param numberOfPlayers is the number of players that the user decided
     */

    public Board(int boardSize, int numberOfPlayers) throws Exception {
        this.numberOfPlayers= numberOfPlayers;
        this.groups = new LinkedList[numberOfPlayers];
        for(int i=0; i<numberOfPlayers; i++) {
            groups[i] = new LinkedList<TileGroup>();
        }

        if(boardSize%2==0) { throw new Exception("illegal boardSize"); }
        boardLength = boardSize; // can't be even
        int halfBoardLength = boardLength/2 +1;

        //  make and fill in the 3d-array with tiles according to this structure:
        // https://www.redblobgames.com/grids/hexagons/
        // But bigger and +4 to all coördinates because negative indexes can't be used in arrays
        // Start with the top row, and than go down till the end

        //first half of board
        this.board = new LogicTile[boardLength][boardLength][boardLength];
        for(int j=0; j<halfBoardLength; j++) {
            for(int i=(boardLength-halfBoardLength)-j; i<boardLength; i++) {
                board[i][boardLength-i][j] = new LogicTile(this);
            }
        }
        //Second half of board
        for(int j=halfBoardLength; j<boardLength; j++) {
            for(int i=j-(boardLength-halfBoardLength); i<boardLength; i++) {
                board[i][boardLength-i][j] = new LogicTile(this);
            }
        }

        //Update what the neighbours are for every tile
        this.board = new LogicTile[boardLength][boardLength][boardLength];
        for(int j=0; j<halfBoardLength; j++) {
            for(int i=(boardLength-halfBoardLength)-j; i<boardLength; i++) {
                LogicTile tempTile = board[i][boardLength-i][j];
                addNeighboursOfTile(tempTile, i, boardLength-i, j);
            }
        }
        for(int j=halfBoardLength; j<boardLength; j++) {
            for(int i=j-(boardLength-halfBoardLength); i<boardLength; i++) {
                LogicTile tempTile = board[i][boardLength-i][j];
                addNeighboursOfTile(tempTile, i, boardLength-i, j);
            }
        }
    }

    //Update what the neighbours are
    public void addNeighboursOfTile(LogicTile tile, int x, int y, int z) {
        // TODO: simplify it
        //have to check 6 places, 2 for each direction,
        // check where x stays the same, y+1, z+1, and check where y-1, z+1
        // and then for every direction (x, y and z)
        if(isLegalcoordinates(x,y,z)) {
            if (isLegalcoordinates(x,y+1,z-1) ) {
                tile.addNeighbour(board[x][y + 1][z - 1]);
            }
            if (isLegalcoordinates(x,y-1,z+1)) {
                tile.addNeighbour(board[x][y - 1][z + 1]);
            }
            if (isLegalcoordinates(x+1, y,z-1)) {
                tile.addNeighbour(board[x + 1][y][z - 1]);
            }
            if (isLegalcoordinates(x-1, y,z+1)) {
                tile.addNeighbour(board[x - 1][y][z + 1]);
            }
            if (isLegalcoordinates(x-1,y+1,z)) {
                tile.addNeighbour(board[x - 1][y + 1][z]);
            }
            if (isLegalcoordinates(x+1,y-1,z)){
                tile.addNeighbour(board[x + 1][y - 1][z]);
            }
        }
    }

    /**
     * check if a tile can be coloured
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @param c colour that can be used to colour the tile
     * @throws Exception if is not that players turn or if the tile has been coloured by an illegal colour
     */

    public void move(int x, int y, int z, int c) throws Exception {
        //check if a legal colour
        if(c<=0 || c> numberOfPlayers) {
            throw new Exception("illegal Colour");
        }

        //check if that colours turn
        if(playerTurn != c) {
            throw new Exception("Not that players turn");
        }

        //check if that tile can be coloured by that colour
        LogicTile tile = board[x][y][z];
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
     * check if the next move is legal
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @param c colour
     * @return true or false if the tile can be coloured by that colour or not
     */
    public boolean isLegal(int x, int y, int z,int c)
    {
        LogicTile t= board[x][y][z];
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
         if(x>boardLength || y>boardLength || z>boardLength )
         {
             return false;
         }
         return true;
     }

    /**
     *
     * @return the length of the board
     */
    public int BoardSize() {return boardLength;}

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