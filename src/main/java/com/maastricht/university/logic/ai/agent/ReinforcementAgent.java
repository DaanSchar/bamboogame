package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.reinforcement.network.NeuralGameState;
import com.maastricht.university.logic.ai.reinforcement.network.Network;
import com.maastricht.university.logic.game.components.Hexagon;
import com.maastricht.university.logic.game.components.LogicTile;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.List;

public class ReinforcementAgent extends Agent {

    private MultiLayerNetwork multiLayerNetwork;

    public ReinforcementAgent(IGameState gameState, int playerNumber, String networkFile) {
        super(gameState, playerNumber);
        this.multiLayerNetwork = Network.loadNetwork(networkFile);
    }

    public void move() {
        if (getLegalMoves().size() == 0)
            return;

        Move move = getMove();
        super.gameState.move(move.getX(), move.getY(), super.getPlayer());
    }

    /**
     * Returns the move calculated by the network
     * @return
     */
    private Move getMove() {
        double[] output = getNetworkOutput();
        int index = getIndexOfLargestValue(output);
        List<Move> moveList = getLegalMoves();

        if (moveList.size() == 0)
            return null;

        return moveList.get(index * moveList.size()/Network.NUM_INPUTS);
    }

    /**
     * Loads and returns output layer of the network
     */
    private double[] getNetworkOutput() {
        NeuralGameState state = new NeuralGameState(gameStateVector(super.gameState));
        INDArray output = multiLayerNetwork.output(state.getMatrix(), false);

        return output.data().asDouble();
    }

    /**
     * Returns the index of the highest value of an array
     */
    private int getIndexOfLargestValue(double[] array) {
        int maxValueIndex = 0;

        for (int j = 0; j < array.length; j++)
            if (array[j] > array[maxValueIndex] )
                maxValueIndex = j;

        return maxValueIndex;
    }

    /**
     * Returns 1D array version of the Hexagon board, where the values
     * represent the color of the player.
     */
    private double[] gameStateVector(IGameState game) {
        List<LogicTile> logicVector = game.getBoard().getTileMap().vector();
        double[] vector = new double[logicVector.size()];

        for (int i = 0; i < logicVector.size(); i++)
            vector[i] = logicVector.get(i).getPlayerColor();

        return vector;
    }

    /**
     * Returns a list of moves that are legal
     */
    private List<Move> getLegalMoves() {
        return removeIllegalMoves(
                ((Hexagon<LogicTile>)super.gameState.getBoard().getTileMap())
                        .moveVector()
        );
    }

    /**
     * removes illegal moves from a list of moves
     */
    private List<Move> removeIllegalMoves(List<Move> moveList) {
        List<Move> legalList = new ArrayList<>();
        for (int i = 0; i < moveList.size(); i++)
            if (super.gameState.isLegal(moveList.get(i).getX(), moveList.get(i).getY(), super.getPlayer()))
                legalList.add(moveList.get(i));

        return legalList;
    }

}
