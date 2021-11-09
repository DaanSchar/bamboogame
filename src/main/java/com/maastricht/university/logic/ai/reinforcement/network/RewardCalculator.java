package com.maastricht.university.logic.ai.reinforcement.network;

import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;

public class RewardCalculator {

    public static double get(GameState gameState, Move move, int player) {
        if (gameState.winner() == player)
            return 100;

        double reward = 1;
        reward += getDifferenceOfTotalGroups(gameState, getPotentialState(gameState, move, player), player);

        System.out.println("Reward: " + reward);

        return reward;
    }

    private static int getDifferenceOfTotalGroups(GameState stateA, GameState stateB, int player) {
        int totalA = stateA.getTotalGroups(player);
        int totalB = stateB.getTotalGroups(player);

        return totalA - totalB;
    }

    private static GameState getPotentialState(GameState gameState, Move move, int player) {
        GameState potentialState = gameState.clone();
        potentialState.move(move.getX(), move.getY(), player);

        return potentialState;
    }
}
