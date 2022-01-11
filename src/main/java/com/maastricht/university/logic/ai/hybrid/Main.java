package com.maastricht.university.logic.ai.hybrid;


import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.agent.RandomAgent;
import com.maastricht.university.logic.ai.hybrid.network.Train;
import com.maastricht.university.logic.ai.minimax.functions.RandomEval;
import com.maastricht.university.logic.ai.reinforcement.network.Evaluation;
import com.maastricht.university.logic.ai.minimax.functions.StandardEval;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.ai.minimax.functions.ReinforceEval;

public class Main {
    public static void main(String[] args) {
        train();
//        evaluate();
    }

    static void train() {
        long startTime = System.currentTimeMillis();
        Train.train();
        System.out.println("Total Training time: " + (System.currentTimeMillis() - startTime));
    }

    static void evaluate() {
        String networkName = "src/main/resources/networks/evaluationNetwork/newNetwork/network-hybrid-score-0.01-99.zip";
        Evaluation.evaluateNetwork(
                new AlphaBetaAgent(
                        new GameState(4, 2),
                        1,
                        2,
                        new RandomEval()
                ),
                new AlphaBetaAgent(
                        new GameState(4, 2),
                        2,
                        2,
                        new ReinforceEval(networkName)
                )
        );
    }
}
