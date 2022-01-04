package com.maastricht.university.logic.ai.hybrid.environment;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.RandomAgent;
import com.maastricht.university.logic.ai.hybrid.network.Network;
import com.maastricht.university.logic.game.game.GameState;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

public class Environment implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private DiscreteSpace actionSpace = new DiscreteSpace(Network.NUM_INPUTS);
    private GameState game = new GameState(4,2);
    private Agent opponent = new RandomAgent(game, 2);// we can change the opponent we want to train against here

    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        return null;
    }

    @Override
    public ObservationSpace<NeuralGameState> getObservationSpace() {
        return new GameObservationSpace();
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return actionSpace;
    }

    @Override
    public NeuralGameState reset() {
        game = new GameState(4,2);
        opponent.setGameState(game);
        return new NeuralGameState(game.getStateVector());
    }

    @Override
    public boolean isDone() {
        return game.isGameOver();
    }

    @Override
    public MDP<NeuralGameState, Integer, DiscreteSpace> newInstance() {
        return new Environment();
    }

    @Override
    public void close() {

    }
}
