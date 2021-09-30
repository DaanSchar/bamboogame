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
     * @param numberOfPlayers number of players that play
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

    public void setBoard(Board newBoard) {
        this.board = newBoard;
    }

    @Override
    public GameState clone() {
        GameState cloneGameState = new GameState(this.board.getBoardSize(), this.numberOfPlayers);
        Board cloneBoard = board.clone();
        cloneGameState.setBoard(cloneBoard);
        return cloneGameState;
    }


    /**
     * returns the size of the group this tile will reside in once
     * it gets colored
     * @param tile
     * @return size of the to be created group
     */
    private int getNewGroupSize(LogicTile tile, int playerColor) {
        List<TileGroup> groups = getNeighboringGroups(tile, playerColor);
        int size = 0;

        for (TileGroup group : groups)
            size += group.getMembers().size();

        return size + 1;
    }

    /**
     * when we color a tile which is neighboring one or multiple groups,
     * these groups will merge, decreasing the amount of total groups.
     * @return total groups if tile were to be colored
     */
    private int getNewTotalGroups(LogicTile tile, int playerColor) {
        int totalGroups = board.getGroups(playerColor).size();
        int totalNeighboringGroups = getNeighboringGroups(tile, playerColor).size();
        return totalGroups+1 - (totalNeighboringGroups);
    }

    /**
     * since getting the group of each neighbor results in having
     * duplicate groups if 2 neighbors are in the same group, we
     * have to filter this this list for duplicates.
     * @param tile
     * @return list of unique groups of the neighbors of tile
     */
    private List<TileGroup> getNeighboringGroups(LogicTile tile, int playerColor) {
        return board.getNeighboringGroups(tile.getQ(), tile.getR(), playerColor);
    }


    /**
     * a neighbor of a tile is defined by its location in the hexagon structure.
     * if a tile is adjacent to our tile, it is considered a neighbor.
     * @param tile
     * @return list of adjacent tiles
     */
    private List<LogicTile> getNeighbors(LogicTile tile) {
        return board.getTileMap().getNeighbours(tile.getQ(), tile.getR());
    }

    /**
     * throws the exception that need to be caught inside move()
     */
    private void findIllegalException(int q, int r, int playerColor) throws Exception {
        if (board.getTileMap().get(q, r) == null)
            throw new OutsideHexagonException("Coordinates are outside tilemap (hexagon)");
        if (!isLegal(q, r, playerColor))
            throw new IllegalMoveException("Move is Illegal");
        if (playerColor != playerTurn)
            throw new IllegalMoveException("It is not their turn.");
        if (playerColor > numberOfPlayers)
            throw new IllegalMoveException("there are only " + numberOfPlayers + " players.");
        if (playerColor < 1)
            throw new IllegalMoveException("player must be bigger than 0");
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    private int getNextPlayer() {
        int nextPlayer = playerTurn+1;

        if (nextPlayer > numberOfPlayers)
            return 1;
        return nextPlayer;
    }


}