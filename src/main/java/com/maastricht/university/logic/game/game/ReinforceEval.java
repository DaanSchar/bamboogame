package com.maastricht.university.logic.game.game;

import com.maastricht.university.logic.ai.reinforcement.environment.NeuralGameState;
import com.maastricht.university.logic.ai.reinforcement.network.Network;
import com.maastricht.university.logic.game.components.LogicTile;
import com.maastricht.university.logic.game.util.interfaces.IEvaluationFunction;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;

public class ReinforceEval implements IEvaluationFunction {

    private MultiLayerNetwork multiLayerNetwork;

    public ReinforceEval(String networkFile) {
        this.multiLayerNetwork = Network.loadNetwork(networkFile);
    }

    @Override
    public int getPlayerScore(IGameState state, int player) {
        return getScore(state);
    }

    private double[] getNetworkOutput(IGameState gameState) {
        NeuralGameState state = new NeuralGameState(gameStateVector(gameState));
        INDArray output = multiLayerNetwork.output(state.getMatrix(), false);

        return output.data().asDouble();
    }

    private double[] gameStateVector(IGameState state) {
        List<LogicTile> logicVector = state.getBoard().getTileMap().vector();
        double[] vector = new double[logicVector.size()];

        for (int i = 0; i < logicVector.size(); i++)
            vector[i] = logicVector.get(i).getPlayerColor();

        return vector;
    }

    private int getScore(IGameState state) {
        double[] output = getNetworkOutput(state);

        return getIndexOfLargestValue(output);
    }

    private int getIndexOfLargestValue(double[] array) {
        int maxValueIndex = 0;

        for (int j = 0; j < array.length; j++)
            if (array[j] > array[maxValueIndex] )
                maxValueIndex = j;

        return maxValueIndex;
    }
}
