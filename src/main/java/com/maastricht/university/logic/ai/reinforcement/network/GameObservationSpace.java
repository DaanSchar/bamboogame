package com.maastricht.university.logic.ai.reinforcement.network;

import org.deeplearning4j.rl4j.space.ObservationSpace;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;


/**
 * This class represents the observation space of the game ie the Domain of
 * possible states (I think?).
 */
public class GameObservationSpace implements ObservationSpace<NeuralGameState> {
    @Override
    public String getName() {
        return "GameObservationSpace";
    }

    @Override
    public int[] getShape() {
        return new int[] { 1, Network.NUM_INPUTS };
    }

    @Override
    public INDArray getLow() {
        return Nd4j.create(createArray(Network.LOW_VALUE));
    }

    @Override
    public INDArray getHigh() {
        return Nd4j.create(createArray(Network.HIGH_VALUE));
    }

    private static double[] createArray(double value) {
        double[] array = new double[Network.NUM_INPUTS];
        for (int i = 0; i < array.length; i++)
            array[i] = value;

        return array;
    }
}
