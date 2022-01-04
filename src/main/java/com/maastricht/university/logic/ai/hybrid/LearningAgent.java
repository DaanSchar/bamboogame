package com.maastricht.university.logic.ai.hybrid;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

public class LearningAgent extends Agent {

    ITree<IGameState> tree;

    public LearningAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);
    }

    public ITreeNode<IGameState> getNextNode() {
        // get the node that comes after the current node,

        // we need the gamestate of this node,
        // as it serves as the input of the network for the next step.
        return null;
    }

    public ITreeNode<IGameState> getCurrentNode() {
        // get the node which we want to compute the score of,

        // return next null leaf of the tree, aka a node that hasn't been assigned a score
        return null;
    }

    public boolean isNull() {
        //returns true if one of the leaves of the tree is null/not computed
        return false;
    }

    public void move() {
        // if the whole tree has been computed, explore the tree and chose the most optimal move
    }

}
