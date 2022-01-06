package com.maastricht.university.logic.ai.hybrid.environment;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.hybrid.LearningAgent;
import com.maastricht.university.logic.ai.agent.RandomAgent;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.ai.reinforcement.network.RewardCalculator;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

public class Environment implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private final int maxScore = 50;

    private DiscreteSpace actionSpace = new DiscreteSpace(maxScore);
    private GameState game = new GameState(4, 2);
    private Agent opponent = new RandomAgent(game, 1);
    private LearningAgent learningAgent = new LearningAgent(game, 2);


    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        GameState nextState;

        if (learningAgent.isMovable()) {
            opponent.move();
            learningAgent.move();
            learningAgent.initTree();
            nextState = game;
        } else {
            learningAgent.getCurrentNode().setScore(integer);
            ITreeNode<IGameState> nextNode = learningAgent.getNextNode();

            if (nextNode != null)
                nextState = (GameState) nextNode.getElement();
            else
                nextState = game;
        }

        return new StepReply<>(
                new NeuralGameState(nextState.getStateVector()),
                RewardCalculator.getWinReward(game, 2),
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

        learningAgent.setGameState(game);
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
