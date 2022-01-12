package com.maastricht.university.logic.ai.learning.train.launch;

import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.learning.Evaluation;
import com.maastricht.university.logic.ai.minimax.functions.EvaluationAddRandomness;
import com.maastricht.university.logic.ai.minimax.functions.ReinforceEval;
import com.maastricht.university.logic.ai.minimax.functions.StandardEval;
import com.maastricht.university.logic.game.game.GameStateFactory;

public class Evaluate {

    static String file = "src/main/resources/networks/hybrid/newNetwork/thisismynetworkstayaway.zip";

    public static void main(String[] args) {
        evaluate();
    }

    static void evaluate() {
        Evaluation.evaluateNetwork(
                new AlphaBetaAgent(
                        GameStateFactory.getGameState(),
                        1,
                        2,
                        new ReinforceEval(file)
                ),
                new AlphaBetaAgent(
                        GameStateFactory.getGameState(),
                        2,
                        2,
                        new EvaluationAddRandomness(new StandardEval(), 5)
                )
        );
    }

}
