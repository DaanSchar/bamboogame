package com.maastricht.university.logic;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private boolean redsTurn;

    private List<TileGroup>[] groups;

    private LogicTile[][][] board;
    private Hexagon<LogicTile> tileMap; // <--- replacement of LogicTile[][][] board

    // radius of the hexagon
    private int boardSize;
    private int numberOfPlayers;

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

    public int getBoardSize() {return boardSize;}
    public int getNumberOfGroups(int c) {return groups[c-1].size();}
    public List<TileGroup> getGroups(int c) {return groups[c-1];}
    public int getNumberOfGroupsRed() { return groups[0].size();}
    public int getNumberOfGroupsBlue() { return groups[1].size();}

    public boolean isRedsTurn() { return redsTurn; }
    public void setRedsTurn(boolean turnRed) { redsTurn = turnRed; }
}
