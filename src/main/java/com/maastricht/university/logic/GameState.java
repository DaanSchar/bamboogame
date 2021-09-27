package com.maastricht.university.logic;

public class GameState {

    private Board board;

    /**
     * construct the gamestate
     * @param boardSize is the size of the board we are using
     * @param numberOfPlayers number of players that the user typed
     * @throws Exception
     */
    public GameState(final int boardSize, final int numberOfPlayers) throws Exception {
        this.board = new Board(boardSize, numberOfPlayers);

    }


    /**
     * Make sure that you get all the tiles from frontend
     * @param x coordinate
     * @param y coordinate
     * @param c color
     * @throws Exception
     */
    public void move(int x, int y, int c) throws Exception {
        board.move(x, y, c);
    }

    /**
     *
     * @return player turn
     */
    public int getPlayerTurn() {
        return board.getPlayerTurn();
    }

    /**
     * method that check if the new player move is legal
     * @param x coordinate
     * @param y coordinate
     * @param c colour
     * @return true or false depending on is legal move
     */
    public boolean isLegal(int x, int y, int c) {
        LogicTile tile = board.getHexagon().get(x, y);

        if (tile.getColour() != 0)
            return false;



        return true;
    }
}