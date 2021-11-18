package com.maastricht.university.logic.ai.reinforcement.environment;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.RandomAgent;
import com.maastricht.university.logic.ai.reinforcement.network.Network;
import com.maastricht.university.logic.ai.reinforcement.network.RewardCalculator;
import com.maastricht.university.logic.game.components.Hexagon;
import com.maastricht.university.logic.game.components.LogicTile;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

import java.util.ArrayList;
import java.util.List;

public class Environment implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private DiscreteSpace actionSpace = new DiscreteSpace(Network.NUM_INPUTS);
    private GameState game = new GameState(4,2);
    private Agent opponent = new RandomAgent(game, 2);// we can change the opponent we want to train against here

    /**
     * This is where both agents make a move
     * and the reward of that move get calculated.
     */
    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        List<Move> moveList = getLegalMoves();
        Move move = moveList.get(integer * moveList.size() / Network.NUM_INPUTS);

        game.move(move.getX(), move.getY(), 1);
        opponent.move();

        double reward = RewardCalculator.getWinReward(game, 1);

        NeuralGameState observation = new NeuralGameState(game.getStateVector());

        return new StepReply<>(observation, reward, isDone(), "Bamboo");
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
    public ObservationSpace<NeuralGameState> getObservationSpace() {
        return new GameObservationSpace();
    }

    @Override
    public MDP<NeuralGameState, Integer, DiscreteSpace> newInstance() {
        return new Environment();
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return actionSpace;
    }

    @Override
    public void close() {}

    /**
     * Returns a list of moves that are legal
     */
    private List<Move> getLegalMoves() {
        return removeIllegalMoves(
                ((Hexagon<LogicTile>)game.getBoard().getTileMap())
                        .moveVector()
        );
    }

    /**
     * removes illegal moves from a list of moves
     */
    private List<Move> removeIllegalMoves(List<Move> moveList) {
        List<Move> legalList = new ArrayList<>();

        for (Move move : moveList)
            if (game.isLegal(move.getX(), move.getY(), 1))
                legalList.add(move);

        return legalList;
    }
}
