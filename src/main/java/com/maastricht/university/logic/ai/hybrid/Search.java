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
    public Move searchTree(ITreeNode root){
        ITreeNode node;
        //if the children exist and they have a score
        if(root.hasChildren() && checkNotNull(root.getChildren())){
            maxValue(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
            return root.getMaxChild().getLastMove();
        }else{
            node = (ITreeNode) root.getChildren().get(0);

            if(checkNotNull(node.getChildren())){
                maxValue(node,Integer.MIN_VALUE,Integer.MAX_VALUE);
            }
        }

        return node.getMaxChild().getLastMove();
    }

    /**
     * Check if the children of a node have a score
     * @param children List of children of a node
     * @return true if all children have a score, false if one or more don't
     */
    private boolean checkNotNull(List<ITreeNode> children){
        ArrayList<ITreeNode> list = new ArrayList<>();
        for(int i=0; i< children.size() ; i++){
            ITreeNode child = children.get(i);
            if(child.hasScore()){
                list.add(child);
            }
        }
        return list.equals(children);
    }

    /**
     * computes the maxValue of the branch and adds all children to the tree
     *
     * @param node  root of branch
     * @return the maxValue of the branch
     */
    private int maxValue(ITreeNode node, int alpha, int beta){
        GameState state = (GameState) node.getElement();
        int value = Integer.MIN_VALUE;
        //if it is a leaf node, return current node's score
        if(state.isGameOver()){
            return node.getScore();
        }

        value = Math.max(value, minValue(node.getMaxChild(), alpha, beta));

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
        int value = Integer.MAX_VALUE;
        //if it is a leaf node, return current node's score
        if(state.isGameOver()){
            return node.getScore();
        }

        value = Math.max(value, maxValue(node.getMinChild(), alpha, beta));

        // Prune
        if (value <= alpha)
            return value;
        beta = Math.min(beta, value);

        return value;
    }
}
