package com.maastricht.university.logic.ai.reinforcement.network;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.ReinforcementAgent;
import com.maastricht.university.logic.game.game.GameState;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SnakeDl4j {

    private static final Logger LOG = LoggerFactory.getLogger(SnakeDl4j.class);
    private GameState game = new GameState(4,2);

    public static void main(String[] args) {
        new SnakeDl4j();
    }

    public SnakeDl4j()  {
        train();
    }

    /**
     * Trains the network.
     * (also evaluates it at the end)
     */
    private void train() {
        String randomNetworkName = "src/main/resources/networks/network-" + System.currentTimeMillis() + ".zip";

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

        evaluateNetwork(randomNetworkName, new ReinforcementAgent(game, 2, "src/main/resources/networks/network-1636666442341.zip"));
    }

    /**
     * Evaluates the network on how good it is compared a different agent.
     */
    private void evaluateNetwork(String randomNetworkName, Agent opponentAgent) {
        int totalScore = 0;
        int totalGames = 200;

        Agent RLAgent = new ReinforcementAgent(game, 1, randomNetworkName);

        for (int i = 0; i < totalGames; i++) {
            int score = 0;

            while (!game.isGameOver()) {
                try {
                    RLAgent.move();
                    opponentAgent.move();

                    if (game.winner() == 1)
                        score = 100;
                    else if (game.winner() == 2)
                        score = -100;

                } catch (final Exception e) {
                    LOG.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
            }

            LOG.info("Score of iteration '{}' was '{}'", i, score);
            totalScore+=score;

            game = new GameState(4, 2);
            RLAgent.setGameState(game);
            opponentAgent.setGameState(game);
        }
        LOG.info("Finished evaluation of the network, average score was '{}'", totalScore/totalGames);
    }

    private void trainWithJavaFX() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        train();
                    }
                },
                100
        );
    }
}
