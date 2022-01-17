package com.maastricht.university.logic.ai.learning.train;

import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.hybrid.environment.NeuralGameState;
import com.maastricht.university.logic.ai.learning.Evaluation;
import com.maastricht.university.logic.ai.learning.NetworkFactory;
import com.maastricht.university.logic.ai.learning.networks.INetwork;
import com.maastricht.university.logic.ai.minimax.functions.*;
import com.maastricht.university.logic.game.game.GameStateFactory;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Train {

    private static final Logger LOG = LoggerFactory.getLogger(Train.class);

    /**
     * Trains the network and saves it.
     * (also evaluates it at the end)
     */
    public static void train(INetwork network, MDP environment) {
        LOG.info("Start training network");
        long startTime = System.currentTimeMillis();

        String networkName = network.getName() + "-lR" + NetworkFactory.getLearningRate() + "-mE" + NetworkFactory.getMinEpochs() + "-" + System.currentTimeMillis() + ".zip";
        String location = "src/main/resources/networks/hybrid/test/";

        QLearningDiscreteDense<NeuralGameState> dql = new QLearningDiscreteDense<>(
                environment,
                network.buildDQNFactory(),
                network.buildConfig()
        );

        dql.train();
        environment.close();

        LOG.info("Finished training network '{}'.", networkName);
        LOG.info("Total training time: " + timeToString(System.currentTimeMillis() - startTime) + ".");

        try {
            dql.getNeuralNet().save(location + networkName);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        int evalScore1 = evaluateNetwork(location + networkName, new RandomEval());
        int evalScore2 = evaluateNetwork(location + networkName, new EvaluationAddRandomness(new StandardEval(), 10));

        String evaluatedNetworkName = network.getName() + "-wR" + evalScore1 + "-wR" + evalScore2 + "-lR" + NetworkFactory.getLearningRate() + "-mE" + NetworkFactory.getMinEpochs() + ".zip";

        try {
            dql.getNeuralNet().save(location + evaluatedNetworkName);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        LOG.info("Saving network as '{}'.", evaluatedNetworkName);

        deleteFile(location + networkName); // deletes the first file created.
    }

    private static String timeToString(long time) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(new Date(time));
    }

    private static int evaluateNetwork(String file, IEvaluationFunction opponentEvalFunction) {
        return Evaluation.evaluateNetwork(
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
                        opponentEvalFunction
                )
        );
    }

    private static void deleteFile(String fileName) {
        File file = new File(fileName);

        boolean isDeleted = file.delete();

        if (!isDeleted) {
            LOG.warn("Could not delete file '{}'.", fileName);
        }
    }

}
