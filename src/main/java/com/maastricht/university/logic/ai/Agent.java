package com.maastricht.university.logic.ai;

import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.ArrayList;
import java.util.Random;

public class Agent {

    private IGameState gameState;
    private int player;

    public Agent(IGameState gameState, final int playerNumber) {
        this.gameState = gameState;
        this.player = playerNumber;
    }

    public void move() {
        while (gameState.getPlayerTurn() == 2 && gameState.winner() == 0) {
            determineMove();
        }
    }


    private void determineMove() {
        Random rand = new Random();
        ArrayList<Move> moveList = gameState.getLegalMoves(2);//Im guessing agent is player 2?
        int index = rand.nextInt(moveList.size());
        Move move = moveList.get(index);

        gameState.move(move.getX(), move.getY(), player);
    }
}
