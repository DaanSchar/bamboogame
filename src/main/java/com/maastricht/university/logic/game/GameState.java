package com.maastricht.university.logic.game;

import com.maastricht.university.logic.util.exceptions.GameIsOverException;
import com.maastricht.university.logic.util.exceptions.IllegalMoveException;
import com.maastricht.university.logic.util.exceptions.OutsideHexagonException;
import com.maastricht.university.logic.util.interfaces.IGameState;

import java.util.List;
import java.util.Arrays;

public class GameState implements IGameState {

    private Board board;
    private int playerTurn;
    private int numberOfPlayers;
    private Boolean[] actualPlayers;

    /**
     * construct the gamestate
     * @param boardSize is the size of the board we are using, defined as the diameter of the hexagon.
     * @param numberOfPlayers number of players that the user typed
     */
    public GameState(final int boardSize, final int numberOfPlayers){
    //public GameState(final int boardSize, final int numberOfPlayers)  {
        this.board = new Board(boardSize, numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
        this.actualPlayers = new Boolean[numberOfPlayers];

        actualPlayers[0] = true;
        actualPlayers[1] = true;
        playerTurn = 1;
    }

    @Override
    public void move(int q, int r, int playerColor) {
        try {
            findIllegalException(q,r,playerColor);
            board.move(q, r, playerColor);
            playerTurn = getNextPlayer();

        // This makes it so that if playerColor is the winner (last person to make a move, and others aren't able to anymore)
            // Than playerColor is the only one with value True in actualPlayers
            while(!legalMovesLeft(playerTurn) && playerTurn!=playerColor)
            {
                actualPlayers[playerTurn-1]=false;
                playerTurn=getNextPlayer();
            }

        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("current player is still " + playerTurn);
        }
    }

    /**
     * a player must have enough groups to make a legal move.
     * a move must result in still having enough groups to justify the move's legality.
     */
    @Override
    public boolean isLegal(int q, int r, int playerColor) {
        LogicTile tile = board.getTileMap().get(q, r);

        if (tile.getPlayerColor() != 0)
            return false;

        if (getNeighboringGroups(tile, playerColor).size() == 0)
            return true;

        if (getNewGroupSize(tile, playerColor) > getNewTotalGroups(tile, playerColor))
            return false;

        return true;
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
     * this method needs to return the winning player
     * if no winner yet then we return false and if we have 1 winner then we return true
     * @return
     */
    public boolean isGameOver() {

        int countTrue=0;
        for(int x=0; x<numberOfPlayers; x++) {

           if(actualPlayers[x]== true)
           {
               countTrue++;
           }
        }
        if(countTrue==1)
        {
            return true;
        }
        return false;

    }

    public int winner() {
        if(isGameOver())
            return playerTurn;
        else
            return 0;
    }

    public boolean legalMovesLeft(int playerColor) {
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                if (board.getTileMap().get(i, j) != null)
                    if (isLegal(i, j, playerColor))
                        return true;
            }
        }

            return false;
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
            throw new OutsideHexagonException("Coordinates are outside tilemap, returned null");
        if (!isLegal(q, r, playerColor))
            throw new IllegalMoveException("Move is Illegal");
        if (playerColor != playerTurn)
            throw new IllegalMoveException("It is not their turn.");
        if (playerColor > numberOfPlayers)
            throw new IllegalMoveException("there are only " + numberOfPlayers + " players.");
        if (playerColor < 1)
            throw new IllegalMoveException("player must be bigger than 0");
        if(isGameOver())
            throw  new GameIsOverException("game is already over, " + playerTurn + " is the winner of the game");
    }

    private int getNextPlayer() {
        int nextPlayer = playerTurn+1;

        if (nextPlayer > numberOfPlayers)
            return 1;

        while(!actualPlayers[playerTurn-1])
        {
            nextPlayer = playerTurn + 1;
            if(playerTurn>numberOfPlayers)
            {

                nextPlayer=1;

            }
        }

        return nextPlayer;
    }




}