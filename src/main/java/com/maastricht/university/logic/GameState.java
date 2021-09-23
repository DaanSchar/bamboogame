package com.maastricht.university.logic;

public class GameState {

    private Board board;

    public GameState(int boardSize, int numberOfPlayers) throws Exception {
        this.board = new Board(boardSize, numberOfPlayers);

    }

    //Make sure that you get all the tiles from frontend

    public void move(int x, int y, int z, int c) throws Exception {
        board.move(x, y, z, c);
    }

    public int getPlayerTurn() {
        return board.getPlayerTurn();
    }

    public boolean isLegal(int x, int y, int z, int c) {
        return board.isLegal(x, y, z, c);
    }
}