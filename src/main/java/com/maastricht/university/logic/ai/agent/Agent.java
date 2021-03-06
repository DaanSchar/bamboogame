package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.game.components.Board;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import java.util.ArrayList;
import java.util.Random;

public class Agent implements IAgent{

    protected IGameState gameState;
    protected int player;
    protected boolean isFirstMove;

    public Agent(IGameState gameState, final int playerNumber) {
        this.gameState = gameState;
        this.player = playerNumber;
        this.isFirstMove = true;
    }

    /**
     * Make a move
     */
    public void move() {
        while (gameState.getPlayerTurn() == player && gameState.winner() == 0) {
            determineMove();
        }
    }

    /**
     *
     * @return the player the agent is
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Set the gameState the agent operates in
     * @param gameState the new gameState
     */
    public void setGameState(IGameState gameState) {
        this.gameState = gameState;
    }

    public void reset(IGameState gameState) {
        setGameState(gameState);
        isFirstMove = true;
    }

    /**
     * Make a random legal move
     */
    private void determineRandomMove() {
        Random rand = new Random();
        ArrayList<Move> moveList = gameState.getLegalMoves(player);
        int index = rand.nextInt(moveList.size());
        Move move = moveList.get(index);

        System.out.println("Move: (" +move.getX()+ ", " +move.getY()+ ", " +player+ ")");
        gameState.move(move.getX(), move.getY(), player);
    }

    /**
     * Make a move that doesn't join groups together (if possible), otherwise random
     */
    private void determineMove() {
        Board board = gameState.getBoard();

        Random rand = new Random();
        ArrayList<Move> moveList = gameState.getLegalMoves(player);
        ArrayList<Move> betterMoves = new ArrayList<>();

        //If none of the moves has neighbouring groups of equal color its a non-connecting move
        // hopefully better
        for(Move m : moveList) {
            if(board.getNeighboringGroups(m.getX(), m.getY(), player).size() == 0)
                betterMoves.add(m);
        }

        if(betterMoves.size() != 0) {
            int index = rand.nextInt(betterMoves.size());
            Move move = betterMoves.get(index);
            gameState.move(move.getX(), move.getY(), player);
            System.out.println("Move: (" + move.getX() + ", " + move.getY() + ", " + player + ")");
        }
        else {
            int index = rand.nextInt(moveList.size());
            Move move = moveList.get(index);
            gameState.move(move.getX(), move.getY(), player);
            System.out.println("Move: (" + move.getX() + ", " + move.getY() + ", " + player + ")");
        }
    }

    /**
     *
     * @return current state of game
     */
    public IGameState getGameState() {
        return gameState;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public String getName() {
        return "Agent";
    }

}
