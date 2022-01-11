package com.maastricht.university.logic.ai.minimax.functions;


import com.maastricht.university.logic.game.util.interfaces.IGameState;

/**
 * this interface makes sure that the evaluation function always has the same functions
 * This way the evaluation function (for minimax search) can easily be changed
 */
public interface IEvaluationFunction {

    /**
     * Uses an heuristic to calculate the score of a player
     * @param state the current state of the game
     * @param player the player whose score gets calculated
     * @return the score of the player
     */
    public int getPlayerScore(IGameState state, int player);

}
