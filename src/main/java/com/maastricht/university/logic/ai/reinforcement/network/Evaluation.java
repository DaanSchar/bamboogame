package com.maastricht.university.logic.ai.reinforcement.network;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.RandomAgent;
import com.maastricht.university.logic.ai.agent.ReinforcementAgent;
import com.maastricht.university.logic.game.game.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Evaluation {

    private static GameState game = new GameState(4,2);
    private static final Logger LOG = LoggerFactory.getLogger(Evaluation.class);

    private static final int TOTAL_GAMES = 100;
    private static Agent opponentAgent = new RandomAgent(game, 2);

    /**
     * Evaluates the network on how good it is compared a different agent.
     */
    public static void evaluateNetwork(String randomNetworkName) {
        LOG.info("Evaluating network: " + randomNetworkName);
        int totalScore = 0;

        Agent RLAgent = new ReinforcementAgent(game, 1, randomNetworkName);
        opponentAgent.setGameState(game);

        for (int i = 0; i < TOTAL_GAMES; i++) {
            int score = playGame(RLAgent, opponentAgent);
            totalScore += score;
            resetGame(RLAgent, opponentAgent);

            LOG.info("Score of iteration '{}' was '{}'", i, score);
        }
        LOG.info("Finished evaluation of the network, average score was '{}'", totalScore/TOTAL_GAMES);
    }

    public static void evaluateNetwork(Agent agent1, Agent agent2) {
        LOG.info("Evaluating network: ");
        int totalScore = 0;

        agent1.setGameState(game);
        agent2.setGameState(game);

        for (int i = 0; i < TOTAL_GAMES; i++) {
            int score = playGame(agent1, agent2);

            totalScore += score;
            resetGame(agent1, agent2);

            LOG.info("Score of iteration '{}' was '{}'", i, score);
        }
        LOG.info("Finished evaluation of the network, average score was '{}'", totalScore/TOTAL_GAMES);
        System.out.println(totalScore);
    }

    /**
     * Plays a game between the two agents.
     * returns the score of the game.
     */
    private static int playGame(Agent a1, Agent a2) {
        int score = 0;

        while (!game.isGameOver()) {
            try {
                a1.move();
                a2.move();
                score = getGameScore();
            } catch (final Exception e) {
                LOG.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }

        return score;
    }

    /**
     * Returns the score of the game.
     */
    private static int getGameScore() {
        if (game.winner() == 1)
            return 100;
        else if (game.winner() == 2)
            return -100;
        return 0;
    }

    /**
     * Resets the game.
     */
    private static void resetGame(Agent a1, Agent a2) {
        game = new GameState(4, 2);
        a1.reset(game);
        a2.reset(game);
    }

}
