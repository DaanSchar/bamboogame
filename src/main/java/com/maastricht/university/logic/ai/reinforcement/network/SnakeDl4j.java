package com.maastricht.university.logic.ai.reinforcement.network;

import com.maastricht.university.frontend.Factory;
import org.apache.commons.math3.analysis.function.Log;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SnakeDl4j {

    private static final Logger LOG = LoggerFactory.getLogger(SnakeDl4j.class);

    public SnakeDl4j() {
        Thread thread = new Thread(() -> {
            String randomNetworkName = "network-" + System.currentTimeMillis() + ".zip";

            Environment mdp = new Environment();
            QLearningDiscreteDense<GameState> dql = new QLearningDiscreteDense<GameState>(
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

            Factory.resetGameState();
        });

        thread.start();
    }
}
