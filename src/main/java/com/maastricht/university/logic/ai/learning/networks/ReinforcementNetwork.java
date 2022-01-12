package com.maastricht.university.logic.ai.learning.networks;

import com.maastricht.university.logic.ai.learning.NetworkFactory;
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
public class ReinforcementNetwork implements INetwork{

    public static final int NUM_INPUTS = 61;
    public static final double LOW_VALUE = 0;
    public static final double HIGH_VALUE = 2;
    private static final int stepsPerEpoch = 100;

    public QLearningConfiguration buildConfig() {
        return QLearningConfiguration.builder()
                .seed(123L)
                .maxEpochStep(stepsPerEpoch)
                .maxStep(stepsPerEpoch * NetworkFactory.getMinEpochs())
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

    public DQNFactoryStdDense buildDQNFactory() {
        DQNDenseNetworkConfiguration build = DQNDenseNetworkConfiguration.builder()
                .l2(0.001)
                .updater(new RmsProp(0.000025))
                .numHiddenNodes(300)
                .numLayers(NetworkFactory.getNumLayers())
                .learningRate(NetworkFactory.getLearningRate())
                .build();

        return new DQNFactoryStdDense(build);
    }

    @Override
    public String getName() {
        return "Reinforcement";
    }
}
