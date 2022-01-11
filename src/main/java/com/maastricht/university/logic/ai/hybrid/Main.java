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
  //      evaluate();
//        evaluateRandom();
    }

    static void train() {
        long startTime = System.currentTimeMillis();
        Train.train();
        System.out.println("Total Training time: " + (System.currentTimeMillis() - startTime));
    }

    static void evaluate() {
        GameState game = new GameState(4, 2);
        String networkName = "evaluationNetwork/newNetwork/network-hybrid-74-0.01-400.zip";
        Evaluation.evaluateNetwork(
                new AlphaBetaAgent(
                        game,
                        1,
                        2,
                        new RandomEval()
                ),
                new AlphaBetaAgent(
                        game,
                        2,
                        2,
                        new ReinforceEval(networkName)
                )
        );
    }

    static void evaluateRandom() {
        GameState game = new GameState(4, 2);
        String networkName = "evaluationNetwork/newNetwork/network-hybrid-83-0.01-50.zip";
        Evaluation.evaluateNetwork(
                new RandomAgent(
                        game,
                        1
                ),
                new AlphaBetaAgent(
                        game,
                        2,
                        2,
                        new ReinforceEval(networkName)
                )
        );
    }
}
