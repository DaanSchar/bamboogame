package com.maastricht.university.logic;

public class GameState {

    private boolean redsTurn;
    private Board board;

    public GameState(int boardSize) throws Exception {
        this.redsTurn = true;
        this.board = new Board(boardSize);

    }

    //Make sure that you get all the tiles from frontend

    public void moveBlue(int x, int y, int z) throws Exception {
        board.move(x, y, z, 2);
    }

    public void moveRed(int x, int y, int z) throws Exception {
        board.move(x, y, z, 1);
    }

    public void move(int x, int y, int z, int c) throws Exception {
        board.move(x, y, z, c);
    }

    public boolean isRedsTurn() {
        return redsTurn;
    }

    public boolean isMoveLegal(int x, int y, int z, int c) {
        return board.isMoveLegal(x, y, z, c);
    }
}
