package com.maastricht.university.logic.ai.hybrid.network;

import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.hybrid.environment.Environment;
import com.maastricht.university.logic.ai.hybrid.environment.NeuralGameState;
import com.maastricht.university.logic.ai.minimax.functions.RandomEval;
import com.maastricht.university.logic.ai.minimax.functions.ReinforceEval;
import com.maastricht.university.logic.ai.reinforcement.network.Evaluation;
import com.maastricht.university.logic.game.game.GameState;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Train {

    private static final Logger LOG = LoggerFactory.getLogger(Train.class);

    /**
     * Trains the network and saves it to src/main/resources/networks.
     * (also evaluates it at the end)
     */
    public static void train() {
        LOG.info("Start training network");
        String randomNetworkName = "src/main/resources/networks/evaluationNetwork/newNetwork/network-hybrid-score-learningRate-maxEpochs" + System.currentTimeMillis() + ".zip";

        Environment mdp = new Environment();
        QLearningDiscreteDense<NeuralGameState> dql = new QLearningDiscreteDense<>(
                mdp,
                Network.buildDQNFactory(),
                Network.buildConfig()
        );

        dql.train();
        mdp.close();

        try {
            dql.getNeuralNet().save(randomNetworkName);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        System.out.println("Finished training network");
        GameState game = new GameState(4,2);
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
                        new ReinforceEval(randomNetworkName)
                )
        );
    }

}
