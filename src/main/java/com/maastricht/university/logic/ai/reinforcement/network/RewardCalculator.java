package com.maastricht.university.logic.ai.reinforcement.network;

import com.maastricht.university.logic.game.game.GameState;

/**
 * Calculates the reward for a given game state.
 */
public class RewardCalculator {

    public static double getWinReward(GameState gameState, int player) {
        if (gameState.winner() == player)
            return 100;

        if (gameState.winner() != player && gameState.winner() != 0)
            return -100;

        return  0;
    }

}
