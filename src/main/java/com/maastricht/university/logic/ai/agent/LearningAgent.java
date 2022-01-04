package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class LearningAgent extends Agent {

    public LearningAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);
    }

    public ITreeNode<IGameState> getNextLeaf() {
        // get the node which we want to compute the score of

        // return next null leaf of the tree
        return null;
    }

    public void move() {
        // if the whole tree has been computed, explore the tree and chose the most optimal move
    }

}
