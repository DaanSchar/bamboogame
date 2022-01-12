package com.maastricht.university.logic.ai.learning.networks;

import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;

public interface INetwork {

    QLearningConfiguration buildConfig();

    DQNFactoryStdDense buildDQNFactory();

    String getName();

}
