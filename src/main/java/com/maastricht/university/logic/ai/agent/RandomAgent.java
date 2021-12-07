package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.ArrayList;
import java.util.Random;

public class RandomAgent extends Agent {

    public RandomAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);
    }

    /**
     * Make a random legal move
     */
    public void move() {
        while (getGameState().getPlayerTurn() == super.getPlayer() && getGameState().winner() == 0) {
            determineRandomMove();
        }
    }

    /**
     * Make a random legal move
     */
    private void determineRandomMove() {
        Random rand = new Random();
        ArrayList<Move> moveList = getGameState().getLegalMoves(getPlayer());
        int index = rand.nextInt(moveList.size());
        Move move = moveList.get(index);

        getGameState().move(move.getX(), move.getY(), getPlayer());
    }

}
