package com.maastricht.university.logic.ai.minimax.functions;

import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class RandomEval implements IEvaluationFunction{

    @Override
    public int getPlayerScore(IGameState state, int player) {

        if (state.isGameOver())
            if (state.winner() == player) {
                if (player == 1)
                    return Integer.MAX_VALUE;
                if (player == 2)
                    return Integer.MIN_VALUE;
            }

        return (int) (Math.random() * 100);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
