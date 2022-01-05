package com.maastricht.university.logic.ai.hybrid.environment;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.hybrid.LearningAgent;
import com.maastricht.university.logic.ai.agent.RandomAgent;
import com.maastricht.university.logic.ai.reinforcement.network.RewardCalculator;
import com.maastricht.university.logic.game.game.GameState;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

import java.util.Arrays;

public class Environment implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private final int maxScore = 50;

    private DiscreteSpace actionSpace = new DiscreteSpace(maxScore);
    private GameState game = new GameState(4,2);
    private Agent opponent = new RandomAgent(game, 2);
    private LearningAgent learningAgent = new LearningAgent(game, 2);

    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        // TODO: make this step function iteratively set the score of leaves of a minimax tree.
        // has to make an initial move to not be zero.
        GameState nextState;
        boolean isDoneComputingTree = !learningAgent.isNull();

        if (isDoneComputingTree) {
            learningAgent.move();
            nextState = game;
            System.out.println("computing tree");
        } else if (learningAgent.isInitialMove()) {
            learningAgent.firstMove();
            nextState = game;
            System.out.println(Arrays.toString(nextState.getStateVector()));
        } else {
            learningAgent.getCurrentNode().setScore(integer);
            nextState = (GameState) learningAgent.getNextNode().getElement();
            System.out.println("learning agent move");
        }


        return new StepReply<>(new NeuralGameState(
                nextState.getStateVector()),
                RewardCalculator.getWinReward(game, 1),
                isDone(),
                null
        );
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
