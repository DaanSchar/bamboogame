package com.maastricht.university.logic.ai.reinforcement.network;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.nd4j.linalg.learning.config.RmsProp;

import java.io.File;
import java.io.IOException;

/**
 * Here we configure our neural network and dqn (RL) algorithm.
 */
public class Network {

    public static final int NUM_INPUTS = 61;
    public static final double LOW_VALUE = 0;
    public static final double HIGH_VALUE = 2;

    private static final int stepsPerEpoch = 29;
    private static final int maxEpochs = 1000;

    public static QLearningConfiguration buildConfig() {
        return QLearningConfiguration.builder()
                .seed(123L)
                .maxEpochStep(stepsPerEpoch)
                .maxStep(stepsPerEpoch * maxEpochs)
                .expRepMaxSize(1500000)
                .batchSize(128)
                .targetDqnUpdateFreq(500)
                .updateStart(10)
                .rewardFactor(0.01)
                .gamma(0.99)
                .errorClamp(1.0)
                .minEpsilon(0.1f)
                .epsilonNbStep(1000)
                .doubleDQN(true)
                .build();
    }

    public static DQNFactoryStdDense buildDQNFactory() {
        DQNDenseNetworkConfiguration build = DQNDenseNetworkConfiguration.builder()
                .l2(0.001)
                .updater(new RmsProp(0.000025))
                .numHiddenNodes(61)
                .numLayers(1)
                .learningRate(0.01)
                .build();

        return new DQNFactoryStdDense(build);
    }

    public static MultiLayerNetwork loadNetwork(String networkName) {
        try {
            return MultiLayerNetwork.load(new File(networkName), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
