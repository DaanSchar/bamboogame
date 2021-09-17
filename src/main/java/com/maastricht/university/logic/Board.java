package com.maastricht.university.logic;

//TODO: add list of all groups to board (implement logic in TileGroup)

import java.util.LinkedList;
import java.util.List;

public class Board {
    private boolean redsTurn;

    private int numberOfGroupsBlue;
    private int numberOfGroupsRed;
    private List<TileGroup>[] groups;

    private LogicTile[][][] board;
    private int boardLength;

    public Board(int boardSize, int numberOfPlayers) throws Exception {
        this.numberOfGroupsBlue = 0;
        this.numberOfGroupsRed = 0;
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
        if(y!=boardLength && z!=0) {
            tile.addNeighbour(board[x][y + 1][z - 1]);
        }
        if(y!=0 && z!=boardLength) {
            tile.addNeighbour(board[x][y - 1][z + 1]);
        }
        if(x!=boardLength && z!=0) {
            tile.addNeighbour(board[x + 1][y][z - 1]);
        }
        if(x!=0 && z!=boardLength) {
            tile.addNeighbour(board[x - 1][y][z + 1]);
        }
        if(x!=0 && y!=boardLength) {
            tile.addNeighbour(board[x - 1][y + 1][z]);
        }
        if(x!=boardLength && y!=0) {
            tile.addNeighbour(board[x + 1][y - 1][z]);
        }
    }

    public void move(int x, int y, int z, int c) throws Exception {
        //check if a legal colour
        if(!(c==1 || c==2)) {
            throw new Exception("illegal Colour");
        }

        //check if that colours turn
        if((redsTurn && c!=1) || (!redsTurn && c==1)) {
            throw new Exception("Not that colours turn");
        }

        //check if that tile can be coloured by that colour
        LogicTile tile = board[x][y][z];
        boolean canColour = false;
        if(c==1) {
            canColour = tile.isLegalForRed();
        }
        else if(c==2) {
            canColour = tile.isLegalForBlue();
        }

        //if it can't be coloured, throw an exception
        if(canColour) {
            tile.setColour(c);
        }
        else {
            throw new Exception("illegal move");
        }
    }

    public boolean isMoveLegal(int x, int y, int z, int c){
        //check if a legal colour
        if(!(c==1 || c==2)) {
            return false;
        }

        //check if that colours turn
        if((redsTurn && c!=1) || (!redsTurn && c==1)) {
            return false;
        }

        //check if move legal for that colour
        if(c==1){
            return board[x][y][z].isLegalForRed();
        }
        else {
            return board[x][y][z].isLegalForBlue();
        }
    }
    public void addGroup(int c, TileGroup group) {
        groups[c-1].add(group);
    }

    public void removeGroup(int c, TileGroup group) {
        groups[c-1].remove(group);
    }

    public int getBoardSize() {return boardLength;}
    public int getNumberOfGroups(int c) {return groups[c-1].size();}

    public int getNumberOfGroupsRed() { return groups[0].size();}
    public int getNumberOfGroupsBlue() { return groups[1].size();}
    public void setNumberOfGroupsRed(int n) {numberOfGroupsRed = n;}
    public void setNumberOfGroupsBlue(int n) {numberOfGroupsBlue = n;}

    public boolean isRedsTurn() { return redsTurn; }
    public void setRedsTurn(boolean turnRed) { redsTurn = turnRed; }
}
