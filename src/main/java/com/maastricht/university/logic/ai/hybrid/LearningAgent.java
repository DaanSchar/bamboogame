package com.maastricht.university.logic.ai.hybrid;

import com.maastricht.university.logic.ai.agent.Agent;
import com.maastricht.university.logic.ai.agent.AlphaBetaAgent;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.game.Evaluation1;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IEvaluationFunction;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.List;

public class LearningAgent extends Agent {

    ITree<IGameState> tree;
    List<ITreeNode> leafNodes;
    IEvaluationFunction eval;
    int depth = 2;

    public LearningAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);

        ComputeLeafNodes computeLeafNodes = new ComputeLeafNodes(gameState, 4);
        leafNodes = computeLeafNodes.getLeafNodes();
        eval = new Evaluation1();
    }

    public ITreeNode<IGameState> getNextNode() {
        // get the node that comes after the current node,

        // we need the gamestate of this node,
        // as it serves as the input of the network for the next step.
        for(int i=0; i<leafNodes.size(); i++) {
            if(!leafNodes.get(i).hasScore()) {
                if(i+1<leafNodes.size())
                    return leafNodes.get(i + 1);
            }
        }
        return null;
    }

    public ITreeNode<IGameState> getCurrentNode() {
        // get the node which we want to compute the score of,

        // return next null leaf of the tree, aka a node that hasn't been assigned a score
        for(int i=0; i<leafNodes.size(); i++) {
            if(!leafNodes.get(i).hasScore())
                return leafNodes.get(i);
        }
        return null;
    }

    public boolean isNull() {
        //returns true if one of the leaves of the tree is null/not computed
        for(int i=0; i<leafNodes.size(); i++) {
            if(!leafNodes.get(i).hasScore())
                return true;
        }
        return false;
    }
    @Override
    public void move() {
        // if the whole tree has been computed, explore the tree and chose the most optimal move
        Search search = new Search(gameState, depth, eval);
        if(this.gameState.winner() == 0){
            Move move = search.searchTree(depth);
            System.out.println("Move for Learning: (" + move.getX() + ", " + move.getY() + ", " + player + ")");
            gameState.move(move.getX(),move.getY(), player);
        }
    }

}
