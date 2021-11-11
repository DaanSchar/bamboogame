package com.maastricht.university.logic.ai.reinforcement.network;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.game.components.Hexagon;
import com.maastricht.university.logic.game.components.LogicTile;
import com.maastricht.university.logic.game.game.Move;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SnakeDl4j {

    private static final Logger LOG = LoggerFactory.getLogger(SnakeDl4j.class);
    private com.maastricht.university.logic.game.game.GameState game = new com.maastricht.university.logic.game.game.GameState(4,2);

    public static void main(String[] args) {
        new SnakeDl4j();
    }

    public SnakeDl4j()  {
//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        train();
//                    }
//                },
//                100
//        );
//        train();
        evaluateNetwork("network-1636660087593.zip");
    }

    private void train() {
        String randomNetworkName = "network-" + System.currentTimeMillis() + ".zip";

        Environment mdp = new Environment();
        QLearningDiscreteDense<NeuralGameState> dql = new QLearningDiscreteDense<NeuralGameState>(
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

//        Factory.resetGameState();

//            evaluateNetwork((com.maastricht.university.logic.game.game.GameState)Factory.getGameState(), randomNetworkName);
    }

    private void evaluateNetwork(String randomNetworkName) {
         MultiLayerNetwork multiLayerNetwork = Network.loadNetwork(randomNetworkName);
        System.out.println("Starting evaluation");
        int highscore = 0;
        int totalScore = 0;
        for (int i = 0; i < 200; i++) {
            int score = 0;
            while (!game.isGameOver()) {
                try {
                     NeuralGameState state = new NeuralGameState(playerColorVector(game));
                     INDArray output = multiLayerNetwork.output(state.getMatrix(), false);
                    double[] data = output.data().asDouble();

                    int maxValueIndex = 0;

                    for (int j = 0; j < data.length; j++)
                        if (data[j] > data[maxValueIndex] )
                            maxValueIndex = j;

                    List<Move> moveList = removeIllegalMoves(((Hexagon<LogicTile>)game.getBoard().getTileMap()).moveVector());
                    Move move = moveList.get(maxValueIndex * moveList.size() / 61);
                    game.move(move.getX(), move.getY(), 1);
//                    WindowUpdater.update(game);
                    Agent agent = new Agent(game, 2);
                    agent.move();
//                    WindowUpdater.update(game);
                    score = !game.isGameOver() ? 0 : game.winner() == 1 ? 100 : -100;


                    // Needed so that we can see easier what is the game doing
                    Network.pause(1);
                } catch (final Exception e) {
                    LOG.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
            }

            LOG.info("Score of iteration '{}' was '{}'", i, score);
            totalScore+=score;
            if (score > highscore) {
                highscore = score;
            }

            // Reset the game
//            Factory.resetGameState();
            game = new com.maastricht.university.logic.game.game.GameState(4, 2);
        }
        LOG.info("Finished evaluation of the network, average score was '{}'", totalScore/2000);
    }


    public List<Move> removeIllegalMoves(List<Move> moveList) {

        List<Move> legalList = new ArrayList<>();
        for (int i = 0; i < moveList.size(); i++)
            if (game.isLegal(moveList.get(i).getX(), moveList.get(i).getY(), 1))
                legalList.add(moveList.get(i));

        return legalList;
    }

    private double[] playerColorVector(com.maastricht.university.logic.game.game.GameState game) {
        List<LogicTile> logicVector = game.getBoard().getTileMap().vector();
        double[] vector = new double[logicVector.size()];

        for (int i = 0; i < logicVector.size(); i++)
            vector[i] = logicVector.get(i).getPlayerColor();

        return vector;
    }
}
