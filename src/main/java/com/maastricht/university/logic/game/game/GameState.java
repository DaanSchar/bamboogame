package com.maastricht.university.logic.game.game;

import com.maastricht.university.logic.game.components.Board;
import com.maastricht.university.logic.game.components.TileGroup;
import com.maastricht.university.logic.game.util.exceptions.GameIsOverException;
import com.maastricht.university.logic.game.util.exceptions.IllegalMoveException;
import com.maastricht.university.logic.game.util.exceptions.OutsideHexagonException;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.ArrayList;
import java.util.List;

public class GameState implements IGameState, Comparable<GameState> {

    private Board board;
    private GameRule gameRules;

    private int playerTurn;
    private int numberOfPlayers;
    private Boolean[] actualPlayers;

    /**
     * construct the gamestate
     * @param boardSize is the size of the board we are using, defined as the radius of the hexagon.
     * @param numberOfPlayers number of players that play
     */
    public GameState(final int boardSize, final int numberOfPlayers) {
        this.board = new Board(boardSize, numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
        this.gameRules = new GameRule(board);
        this.playerTurn = 1;

        this.actualPlayers = new Boolean[numberOfPlayers];
        for(int i=0; i<numberOfPlayers; i++)
            actualPlayers[i] = true;
    }

    @Override
    public void move(int q, int r, int playerColor) {
        try {
            findIllegalException(q,r,playerColor);

            board.move(q, r, playerColor);
            playerTurn = getNextPlayer();

            while(!legalMovesLeft(playerTurn) && playerTurn!=playerColor) {
                actualPlayers[playerTurn-1] = false;
                playerTurn=getNextPlayer();
            }

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
     * this method needs to return the winning player
     * if no winner yet then we return false and if we have 1 winner then we return true
     * @return
     */
    public boolean isGameOver() {
        int countTrue=0;
        for(int x=0; x<numberOfPlayers; x++) {
            if (actualPlayers[x])
                countTrue++;
        }
        return countTrue == 1;
    }

    @Override
    public int winner() {
        if(isGameOver())
            return playerTurn;
        else
            return 0;
    }

    public boolean legalMovesLeft(int playerColor) {
        int maxCoordinate = board.getBoardSize()*2+1;

        for (int i = 0; i < maxCoordinate; i++)
            for (int j = 0; j < maxCoordinate; j++)
                if (board.getTileMap().get(i, j) != null)
                    if (isLegal(i, j, playerColor))
                        return true;

        return false;
    }

    /**
     * @param playerColor the color of the player
     * @return a list containing all legal moves of player playercolor
     */
   public List<Move> getLegalMoves(int playerColor) {
        ArrayList<Move> moveList = new ArrayList<Move>();
        if(!legalMovesLeft(playerColor))
            return null;

        int maxCoordinate = board.getBoardSize()*2+1;

        for (int i = 0; i < maxCoordinate; i++) {
            for (int j = 0; j < maxCoordinate; j++) {
                if (board.getTileMap().get(i, j) != null)
                    if (isLegal(i, j, playerColor))
                        moveList.add(new Move(i,j));
            }
        }

        return moveList;
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
        if(isGameOver())
            throw  new GameIsOverException("game is already over, " + playerTurn + " is the winner of the game");
    }

    private int getNextPlayer() {
        int nextPlayer = playerTurn+1;

        if (nextPlayer > numberOfPlayers)
            nextPlayer = 1;

        while(!actualPlayers[nextPlayer-1]) {
            nextPlayer = nextPlayer + 1;
            if(nextPlayer>numberOfPlayers)
                nextPlayer=1;
        }

        return nextPlayer;
    }

    //TODO: implement score system for gameState
    @Override
    public int compareTo(GameState o) {
        return 0;
    }

}