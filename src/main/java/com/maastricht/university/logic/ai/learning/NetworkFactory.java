package com.maastricht.university.logic.ai.learning;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;
import java.io.IOException;

public class NetworkFactory {

    private static final double LEARNING_RATE = 0.01;
    private static int MIN_EPOCHS = 100;
    private static final int NUM_LAYERS = 1;

    private static final int TOTAL_GAMES = 500; // for evaluation

    public static double getLearningRate() {
        return LEARNING_RATE;
    }

    public static int getMinEpochs() {
        return MIN_EPOCHS;
    }

    public static int getNumLayers() {
        return NUM_LAYERS;
    }

    public static int getTotalGames() {
        return TOTAL_GAMES;
    }

    public static MultiLayerNetwork loadNetwork(String networkName) {
        try {
            return MultiLayerNetwork.load(new File(networkName), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setMinEpochs(int minEpochs) {
        MIN_EPOCHS = minEpochs;
    }
}
