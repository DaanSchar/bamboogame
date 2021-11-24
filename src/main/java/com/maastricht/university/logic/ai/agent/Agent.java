package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import java.util.ArrayList;
import java.util.Random;

public class Agent implements IAgent{

    protected IGameState gameState;
    protected int player;

    public Agent(IGameState gameState, final int playerNumber) {
        this.gameState = gameState;
        this.player = playerNumber;
    }

    public void move() {
        while (gameState.getPlayerTurn() == player && gameState.winner() == 0) {
            determineMove();
        }
    }

    private void determineMove() {
        Random rand = new Random();
        ArrayList<Move> moveList = gameState.getLegalMoves(player);
        int index = rand.nextInt(moveList.size());
        Move move = moveList.get(index);

        System.out.println("Move: (" +move.getX()+ ", " +move.getY()+ ", " +player+ ")");
        gameState.move(move.getX(), move.getY(), player);
    }
}
