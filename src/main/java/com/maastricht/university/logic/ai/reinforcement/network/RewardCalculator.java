package com.maastricht.university.logic.ai.reinforcement.network;

import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;

public class RewardCalculator {

    public static double get(GameState gameState, Move move, int player) {


        if (gameState.winner() == player) {
            System.out.println("winner!");
            return 100;
        }

        if (gameState.winner() != player && gameState.winner() != 0) {
            System.out.println("loser!");
            return -100;
        }

        return 0;
    }


}
