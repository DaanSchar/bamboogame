package com.maastricht.university.logic.ai.minimax.functions;

import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class EvaluationAddRandomness implements IEvaluationFunction {

    private IEvaluationFunction evaFunction;
    private int randomFactor;

    public EvaluationAddRandomness(IEvaluationFunction evaFunction, int randomFactor) {
        this.evaFunction = evaFunction;
        this.randomFactor = randomFactor;
    }

    public EvaluationAddRandomness(IEvaluationFunction evaFunction) {
        this.evaFunction = evaFunction;
        this.randomFactor = 10;
    }

    @Override
    public int getPlayerScore(IGameState state, int player) {
        int randomness = (int) (randomFactor * Math.random());
        return evaFunction.getPlayerScore(state, player) + randomness;
    }

    @Override
    public String getName() {
        return "Randomized " + evaFunction.getName();
    }
}
