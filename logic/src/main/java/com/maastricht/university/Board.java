package com.maastricht.university;

public class Board {

    private Tile[][][] board;

    public Board(int boardSize) throws Exception {
        if(boardSize%2==0) { throw new Exception("illegal boardSize"); }
        int boardLength = boardSize; // can't be even
        int halfBoardLength = boardLength/2 +1;

        //  make and fill in the 3d-array with tiles according to this structure:
        // https://www.redblobgames.com/grids/hexagons/
        // But bigger and +4 to all coördinates because negative indexes can't be used in arrays
        // Start with the top row, and than go down till the end

        //first half of board
        this.board = new Tile[boardLength][boardLength][boardLength];
        for(int j=0; j<halfBoardLength; j++) {
            for(int i=(boardLength-halfBoardLength)-j; i<boardLength; i++) {
                board[i][boardLength-i][j] = new Tile();
            }
        }
        //Second half of board
        for(int j=halfBoardLength; j<boardLength; j++) {
            for(int i=j-(boardLength-halfBoardLength); i<boardLength; i++) {
                board[i][boardLength-i][j] = new Tile();
            }
        }

        //Update what the neighbours are
        //have to check 6 places, for each direction,
        // check where x stays the same, y+1, z+1, and check where y-1, z+1
        // and than for every direction

        //For every tile
        this.board = new Tile[boardLength][boardLength][boardLength];
        for(int j=0; j<halfBoardLength; j++) {
            for(int i=(boardLength-halfBoardLength)-j; i<boardLength; i++) {
                Tile tempTile = board[i][boardLength-i][j];

            }
        }
        for(int j=halfBoardLength; j<boardLength; j++) {
            for(int i=j-(boardLength-halfBoardLength); i<boardLength; i++) {
                Tile tempTile = board[i][boardLength-i][j];
            }
        }
    }


}
