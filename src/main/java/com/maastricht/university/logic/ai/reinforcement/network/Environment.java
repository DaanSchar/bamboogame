package com.maastricht.university.logic.ai.reinforcement.network;

import com.maastricht.university.frontend.Factory;
import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.game.components.Hexagon;
import com.maastricht.university.logic.game.components.LogicTile;
import com.maastricht.university.logic.game.game.Move;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

import java.util.ArrayList;
import java.util.List;

public class Environment implements MDP<GameState, Integer, DiscreteSpace> {

    private DiscreteSpace actionSpace = new DiscreteSpace(61);
    private com.maastricht.university.logic.game.game.GameState game = (com.maastricht.university.logic.game.game.GameState) Factory.getGameState();

    @Override
    public ObservationSpace<GameState> getObservationSpace() {
        return new GameObservationSpace();
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return actionSpace;
    }

    @Override
    public GameState reset() {
        Factory.resetGameState();
        return new GameState(playerColorVector());
    }

    /**
     * converts a 1D array of logic tiles to a 1D array of doubles
     * representing the player color of the tile
     */
    private double[] playerColorVector() {
        List<LogicTile> logicVector = game.getBoard().getTileMap().vector();
        double[] vector = new double[logicVector.size()];

        for (int i = 0; i < logicVector.size(); i++)
            vector[i] = logicVector.get(i).getPlayerColor();

        return vector;
    }

    @Override
    public void close() {}

    @Override
    public StepReply<GameState> step(Integer integer) {
        List<Move> moveList = ((Hexagon<LogicTile>)game.getBoard().getTileMap()).moveVector();
        Move move = moveList.get(integer);

        game.move(move.getX(), move.getY(), 1);
        Network.pause(100);
        Agent agent = new Agent(game, 2);
        agent.move();

        GameState observation = new GameState(playerColorVector());

        double reward = RewardCalculator.get(game, move, 1);

        System.out.println(reward);

        return new StepReply<>(observation, reward, isDone(), "SnakeDL4j");
    }

    @Override
    public boolean isDone() {
        return game.isGameOver();
    }

    @Override
    public MDP<GameState, Integer, DiscreteSpace> newInstance() {
        Factory.resetGameState();
        return new Environment();
    }
}
