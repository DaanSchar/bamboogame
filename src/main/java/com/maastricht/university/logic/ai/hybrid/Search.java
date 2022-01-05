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
    public IEvaluationFunction evaluation;

    public Search(IGameState gameState, int maxDepth, IEvaluationFunction evaluation){
        ComputeLeafNodes computeLeafNodes = new ComputeLeafNodes(gameState, 4);
        tree = computeLeafNodes.getTree();

        if(player == 1) {
            minPlayer = 2;
        }else
            minPlayer = 1;
        this.maxDepth = maxDepth;
        this.evaluation = evaluation;

    }

    /**
     * search for the best move
     * @param depth max depth of the tree
     * @return the best move
     */
    public Move searchTree(int depth){
        ITreeNode root = tree.getRoot();
        int score = maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);

        return root.getMaxChild().getLastMove();

    }

    /**
     * computes the maxValue of the branch and adds all children to the tree
     *
     * @param node  root of branch
     * @param depth max depth of this branch
     * @return the maxValue of the branch
     */
    private int maxValue(ITreeNode node, int alpha, int beta, int depth){
        GameState state = (GameState) node.getElement();
        int value = Integer.MIN_VALUE;

        //if it is a leaf node, return current node's score
        if(state.isGameOver() || depth == 0){
            node.setScore(evaluation.getPlayerScore(state,player));
            return node.getScore();
        }

        //or if it is not, get the node which has the highest value
        List<Move> moves = state.getLegalMoves(player);
        for(int i=0; i<moves.size(); i++) {
            Move move = moves.get(i);
            GameState newState = state.clone();
            newState.move(move.getX(), move.getY(), player);
            node.addChild(newState, move);
            ITreeNode<GameState> newNode = (ITreeNode<GameState>) node.getChildren().get(i);
            value = Math.max(value, minValue(newNode, alpha, beta, depth-1));

            newNode.setScore(value);

            // Prune
            if(value >= beta)
                return value;
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    /**
     * computes the minValue of the branch and adds all children to the tree
     * @param node  root of branch
     * @param depth max depth of this branch
     * @return the minValue of the branch
     */
    private int minValue(ITreeNode node, int alpha, int beta, int depth){
        GameState state = (GameState) node.getElement();
        // if a leaf node, return the current score
        if(state.isGameOver() || depth==0) {
            node.setScore(evaluation.getPlayerScore(state, player));
            return node.getScore();
        }
        // if not a leaf node, get minValue of all children
        int value = Integer.MAX_VALUE;
        List<Move> moves = state.getLegalMoves(minPlayer);

        for(int i=0; i<moves.size(); i++) {
            Move move = moves.get(i);
            GameState newState = state.clone();
            newState.move(move.getX(), move.getY(), minPlayer);
            node.addChild(newState, move);
            ITreeNode<GameState> newNode = (ITreeNode<GameState>) node.getChildren().get(i);
            value = Math.min(value, maxValue(newNode, alpha, beta, depth-1));

            newNode.setScore(value);

            // Prune
            if(value <= alpha)
                return value;
            beta = Math.min(beta, value);
        }
        return value;
    }
}
