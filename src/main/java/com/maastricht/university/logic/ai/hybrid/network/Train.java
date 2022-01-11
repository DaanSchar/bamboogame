package com.maastricht.university.logic.ai.hybrid.network;

import com.maastricht.university.logic.ai.hybrid.environment.Environment;
import com.maastricht.university.logic.ai.hybrid.environment.NeuralGameState;
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
        String randomNetworkName = "src/main/resources/networks/daan/network-hybrid-" + System.currentTimeMillis() + ".zip";

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
//        Evaluation.evaluateNetwork(randomNetworkName);
    }

}
