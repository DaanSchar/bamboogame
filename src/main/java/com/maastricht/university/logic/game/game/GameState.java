package com.maastricht.university.logic.game.game;

import com.maastricht.university.logic.game.components.Board;
import com.maastricht.university.logic.game.components.LogicTile;
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

    private final boolean DEBUG = true;

    /**
     * construct the gamestate
     * @param boardSize is the size of the board we are using, defined as the radius of the hexagon.
     * @param numberOfPlayers number of players that play
     */
    public GameState(final int boardSize, final int numberOfPlayers) {
        init(boardSize, numberOfPlayers);
    }

    /**
     * instantiates the game state
     */
    public void init(final int boardSize, final int numberOfPlayers) {
        this.board = new Board(boardSize, numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
        this.gameRules = new GameRule(board);
        this.playerTurn = 1;

        this.actualPlayers = new Boolean[numberOfPlayers];
        for(int i=0; i<numberOfPlayers; i++)
            actualPlayers[i] = true;
    }



    public int getNumberOfPlayers() {
        return numberOfPlayers;
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
            if (DEBUG) {
                System.out.println(e);
                System.out.println("current player is still " + playerTurn);
            }
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
        return this.board;
    }

    public void setBoard(Board newBoard) {
        this.board = newBoard;
        this.gameRules = new GameRule(newBoard);
    }

    public void setPlayerTurn(int player) {this.playerTurn = player; }

    @Override
    public GameState clone() {
        GameState cloneGameState = new GameState(this.board.getBoardSize(), this.numberOfPlayers);
        Board cloneBoard = board.clone();
        cloneGameState.setBoard(cloneBoard);
        cloneGameState.setPlayerTurn(this.playerTurn);
        return cloneGameState;
    }

    /**
     * this method needs to return the winning player
     * if no winner yet then we return false and if we have 1 winner then we return true
     */
    @Override
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
   public ArrayList<Move> getLegalMoves(int playerColor) {
        ArrayList<Move> moveList = new ArrayList<>();

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
     * @return a vector representing the state of the game ie
     * a 1D array version of the tilemap
     */
   public double[] getStateVector() {
       List<LogicTile> logicVector = board.getTileMap().vector();
       double[] vector = new double[logicVector.size()];

       for (int i = 0; i < logicVector.size(); i++)
           vector[i] = logicVector.get(i).getPlayerColor();

       return vector;
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

    // Shouldn't be used if treeNode score is used correctly, but is a decent fallback
    // always has player 2 as maxPlayer
    public int compareTo(GameState o) {
        int a = getPlayerScore(2);
        int b = o.getPlayerScore(2);

        return Integer.compare(a, b);
    }

    public int getPlayerScore(int player) {
        // if the game is over, return either the max or min score based on whether player or opponent won
        if(winner() != 0) {
            if(winner() == player) {
                //System.out.println("Calculated Score: " + Integer.MAX_VALUE);
                return Integer.MAX_VALUE;
            }
            else{
                //System.out.println("Calculated Score: " + Integer.MIN_VALUE);
                return Integer.MIN_VALUE;
            }
        }

        // + number of groups
        int score = 5*board.getGroups(player).size();

        // + number of legal moves player
        score += getLegalMoves(player).size();

        // - number of legal moves opponent
        // - 5 * number of groups opponent
        if(player == 1) {
            score -= getLegalMoves(2).size();
            score -= 5*board.getGroups(2).size();
        }
        else {
            score -= getLegalMoves(1).size();
            score -= 5 * board.getGroups(1).size();
        }
        //System.out.println("Calculated Score: " +score);
        return score;
    }
}