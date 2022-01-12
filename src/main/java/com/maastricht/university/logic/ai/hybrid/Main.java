package com.maastricht.university.logic.ai.hybrid;


import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.hybrid.environment.HybridEnvironment;
import com.maastricht.university.logic.ai.learning.train.Train;
import com.maastricht.university.logic.ai.learning.networks.HybridNetwork;
import com.maastricht.university.logic.ai.minimax.functions.EvaluationAddRandomness;
import com.maastricht.university.logic.ai.minimax.functions.StandardEval;
import com.maastricht.university.logic.ai.learning.Evaluation;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.ai.minimax.functions.ReinforceEval;

public class Main {
    public static void main(String[] args) {
        train();
//        evaluate();
    }

    static void train() {
        long startTime = System.currentTimeMillis();
        Train.train(new HybridNetwork(), new HybridEnvironment());
        System.out.println("Total Training time: " + (System.currentTimeMillis() - startTime));
    }

    static void evaluate() {
        String networkName = "src/main/resources/networks/evaluationNetwork/newNetwork/thisismynetworkstayaway.zip";
        Evaluation.evaluateNetwork(
                new AlphaBetaAgent(
                        new GameState(4, 2),
                        1,
                        2,
                        new EvaluationAddRandomness(new StandardEval())
                ),
                new AlphaBetaAgent(
                        new GameState(4, 2),
                        2,
                        2,
                        new EvaluationAddRandomness(new ReinforceEval(networkName))
                )
        );
    }
}
