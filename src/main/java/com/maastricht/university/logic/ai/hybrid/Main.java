package com.maastricht.university.logic.ai.hybrid;


import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.agent.RandomAgent;
import com.maastricht.university.logic.ai.hybrid.network.Train;
import com.maastricht.university.logic.ai.reinforcement.network.Evaluation;
import com.maastricht.university.logic.game.game.Evaluation1;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.ReinforceEval;

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
        Evaluation.evaluateNetwork(
//                new AlphaBetaAgent(new GameState(4, 2), 1, 2, new Evaluation1()),
                new RandomAgent(new GameState(4, 2), 1),
                new AlphaBetaAgent(new GameState(4, 2), 2, 2, new ReinforceEval("src/main/resources/networks/evaluationNetwork/semiAB/network-hybrid-96-semiAB-1200-30.zip"))
        );
    }
}
