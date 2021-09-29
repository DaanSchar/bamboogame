package com.maastricht.university.logic.game;

import com.maastricht.university.logic.util.exceptions.IllegalMoveException;
import com.maastricht.university.logic.util.exceptions.OutsideHexagonException;
import com.maastricht.university.logic.util.interfaces.IGameState;

import java.util.List;

public class GameState implements IGameState {

    private Board board;
    private GameRule gameRules;

    private int playerTurn;
    private int numberOfPlayers;

    /**
     * construct the gamestate
     * @param boardSize is the size of the board we are using, defined as the radius of the hexagon.
     * @param numberOfPlayers number of players that the user typed
     */
    public GameState(final int boardSize, final int numberOfPlayers) {
        this.board = new Board(boardSize, numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
        this.gameRules = new GameRule(board);
        playerTurn = 1;
    }

    @Override
    public void move(int q, int r, int playerColor) {
        try {
            findIllegalException(q,r,playerColor);

            board.move(q, r, playerColor);
            playerTurn = getNextPlayer();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("current player is still " + playerTurn);
        }
    }

    @Override
    public boolean isLegal(int q, int r, int playerColor) {
        return gameRules.isLegal(q, r, playerColor);
    }

    @Override
    public int getPlayerTurn() { return playerTurn; }

    @Override
    public int getTotalGroups(int playerColor) {
        return board.getGroups(playerColor).size();
    }

    @Override
    public int getLargestGroupSize(int playerColor) {
        List<TileGroup> groups = board.getGroups(playerColor);
        int max = 0;

        for (TileGroup group : groups)
            if (group.getMembers().size() > max)
                max = group.getMembers().size();

        return max;
    }

    @Override
    public int getPlayerColorOfTile(int q, int r) {
        return board.getTileMap().get(q,r).getPlayerColor();
    }

    public Board getBoard() {
        return board;
    }

    /**
     * throws the exception that need to be caught inside move()
     */
    private void findIllegalException(int q, int r, int playerColor) throws Exception {
        if (board.getTileMap().get(q, r) == null)
            throw new OutsideHexagonException("Coordinates are outside tilemap, returned null");
        if (!isLegal(q, r, playerColor))
            throw new IllegalMoveException("Move is Illegal");
        if (playerColor != playerTurn)
            throw new IllegalMoveException("It is not their turn.");
        if (playerColor > numberOfPlayers)
            throw new IllegalMoveException("there are only " + numberOfPlayers + " players.");
        if (playerColor < 1)
            throw new IllegalMoveException("player must be bigger than 0");
    }

    private int getNextPlayer() {
        int nextPlayer = playerTurn+1;

        if (nextPlayer > numberOfPlayers)
            return 1;
        return nextPlayer;
    }


}