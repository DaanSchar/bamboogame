package com.maastricht.university.logic.ai.hybrid;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.game.Evaluation1;
import com.maastricht.university.logic.game.util.interfaces.IEvaluationFunction;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.List;

public class LearningAgent extends Agent {

    ITree<IGameState> tree;
    List<ITreeNode> leafNodes;
    int indexLeafNodes;

    public LearningAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);

        AlphaBetaAgent ABAgent = new AlphaBetaAgent(gameState, playerNumber, 4, (IEvaluationFunction) new Evaluation1());
        ABAgent.getLeafNodes(4);
        indexLeafNodes = 0;
    }

    public ITreeNode<IGameState> getNextNode() {
        // get the node that comes after the current node,

        // we need the gamestate of this node,
        // as it serves as the input of the network for the next step.

        ITreeNode nextNode = leafNodes.get(indexLeafNodes+1);
        return null;
    }

    public ITreeNode<IGameState> getCurrentNode() {
        // get the node which we want to compute the score of,

        // return next null leaf of the tree, aka a node that hasn't been assigned a score
        ITreeNode currentNode = leafNodes.get(indexLeafNodes);
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
