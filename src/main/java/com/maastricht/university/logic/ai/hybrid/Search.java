package com.maastricht.university.logic.ai.hybrid;

import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IEvaluationFunction;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.List;

public class Search {
    public ITree tree;
    public int player;
    public final int minPlayer;
    public int maxDepth;

    public Search(IGameState gameState, int maxDepth){
        ComputeLeafNodes computeLeafNodes = new ComputeLeafNodes(gameState, 4);
        tree = computeLeafNodes.getTree();

        if(player == 1) {
            minPlayer = 2;
        }else
            minPlayer = 1;
        this.maxDepth = maxDepth;

    }

    /**
     * search for the best move
     * @return the best move
     */
    public Move searchTree(){
        ITree<GameState> currentTree = tree;
        ITreeNode root = currentTree.getRoot();

        if(root.hasChildren()){
            maxValue(root,Integer.MIN_VALUE,Integer.MAX_VALUE);

        }

        return root.getMaxChild().getLastMove();
    }

    /**
     * computes the maxValue of the branch and adds all children to the tree
     *
     * @param node  root of branch
     * @return the maxValue of the branch
     */
    private int maxValue(ITreeNode node, int alpha, int beta){
        GameState state = (GameState) node.getElement();
        int value;
        //if it is a leaf node, return current node's score
        if(state.isGameOver()){
            return node.getScore();
        }

        value = node.getMaxChild().getScore();

        // Prune
        if(value >= beta)
            return value;
        alpha = Math.max(alpha, value);

        return value;
    }

    /**
     * computes the minValue of the branch and adds all children to the tree
     * @param node  root of branch
     * @return the minValue of the branch
     */
    private int minValue(ITreeNode node, int alpha, int beta){
        GameState state = (GameState) node.getElement();
        int value;
        //if it is a leaf node, return current node's score
        if(state.isGameOver()){
            return node.getScore();
        }

        value = node.getMinChild().getScore();

        // Prune
        if (value <= alpha)
            return value;
        beta = Math.min(beta, value);

        return value;
    }
}
