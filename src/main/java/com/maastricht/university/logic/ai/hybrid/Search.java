package com.maastricht.university.logic.ai.hybrid;

import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import java.util.ArrayList;
import java.util.List;

public class Search {
    public ITree tree;
    public int player;
    public final int minPlayer;

    public Search(int player) {
        if(player == 1) {
            minPlayer = 2;
        }else
            minPlayer = 1;
    }

    /**
     * search for the best move
     * @return the best move
     */
    public Move searchTree(ITreeNode root){
        maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return root.getMaxChild().getLastMove();
    }

    /**
     * computes the maxValue of the branch and adds all children to the tree
     *
     * @param node  root of branch
     * @return the maxValue of the branch
     */
    private int maxValue(ITreeNode node, int alpha, int beta) {
        // if the node has a score, return it
        if (node.hasScore())
            return node.getScore();

        int value = Integer.MIN_VALUE;
        // go over all children
        List children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            // keep storing the highest value only
            value = Math.max(value, minValue((ITreeNode) children.get(i), alpha, beta));
        }
        node.setScore(value);

        // Prune
        if(value >= beta)
            return value;
        alpha = Math.max(alpha, value);

        return value;
    }

    /**
     *
     * @param node  root of branch
     * @return the minValue of the branch
     */
    private int minValue(ITreeNode node, int alpha, int beta){
        // if the node has a score, return it
        if (node.hasScore())
            return node.getScore();

        int value = Integer.MAX_VALUE;
        // go over all children
        List children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            // keep storing the highest value only
            value = Math.min(value, maxValue((ITreeNode) children.get(i), alpha, beta));
        }

        node.setScore(value);

        // Prune
        if (value <= alpha)
            return value;
        beta = Math.min(beta, value);

        return value;
    }
}
