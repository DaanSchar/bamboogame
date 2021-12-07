package com.maastricht.university.logic.ai.agent;
import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.ai.minimax.tree.Tree;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.List;

public class AlphaBetaAgent2 extends Agent {

    protected final int minPlayer;
    protected int maxDepth;

    public AlphaBetaAgent2(IGameState gameState, int playerNumber, int maxDepth) {
        super(gameState, playerNumber);
        if (player == 1) {
            minPlayer = 2;
        } else
            minPlayer = 1;
        this.maxDepth = maxDepth;
    }

    /**
     * Make the best move
     */
    @Override
    public void move() {
        if (this.gameState.winner() == 0) {
            Move move = search(this.maxDepth);
            //System.out.println("Move: (" + move.getX() + ", " + move.getY() + ", " + player + ")");
            gameState.move(move.getX(), move.getY(), player);
        }
    }

    /**
     * search for the best move
     *
     * @param depth max depth of the tree
     * @return the best move
     */
    public Move search(int depth) {
        ITree<GameState> tree = new Tree<GameState>((GameState) gameState, 2);
        ITreeNode<GameState> root = tree.getRoot();
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
    private int maxValue(ITreeNode node, int alpha, int beta, int depth) {
        GameState state = (GameState) node.getElement();
        // if a leaf node, return the current score
        if (state.isGameOver() || depth == 0) {
            node.setScore(state.getPlayerScore(player));
            return node.getScore();
        }
        // if not a leaf node, get maxValue of all children
        int value = Integer.MIN_VALUE;
        List<Move> moves = state.getLegalMoves(player);
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            GameState newState = state.clone();
            newState.move(move.getX(), move.getY(), player);
            node.addChild(newState, move);

            ITreeNode<GameState> newNode;
            if(node.getDepth() < 2)
                newNode = (ITreeNode<GameState>) node.getChildren().get(i);
            else
                newNode = (ITreeNode<GameState>) node.getChildren().get(0);

            value = Math.max(value, minValue(newNode, alpha, beta, depth - 1));

            newNode.setScore(value);

            if(node.getDepth() > 2) {
                node.removeChild(0);
            }

            // Prune
            if (value >= beta)
                return value;
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    /**
     * computes the minValue of the branch and adds all children to the tree
     *
     * @param node  root of branch
     * @param depth max depth of this branch
     * @return the minValue of the branch
     */
    private int minValue(ITreeNode node, int alpha, int beta, int depth) {
        GameState state = (GameState) node.getElement();
        // if a leaf node, return the current score
        if (state.isGameOver() || depth == 0) {
            node.setScore(state.getPlayerScore(player));
            return node.getScore();
        }
        // if not a leaf node, get minValue of all children
        int value = Integer.MAX_VALUE;
        List<Move> moves = state.getLegalMoves(minPlayer);
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            GameState newState = state.clone();
            newState.move(move.getX(), move.getY(), minPlayer);
            node.addChild(newState, move);

            ITreeNode<GameState> newNode;
            if(node.getDepth() < 2)
                newNode = (ITreeNode<GameState>) node.getChildren().get(i);
            else
                newNode = (ITreeNode<GameState>) node.getChildren().get(0);

            value = Math.min(value, maxValue(newNode, alpha, beta, depth - 1));

            newNode.setScore(value);

            if(node.getDepth() > 2) {
                node.removeChild(0);
            }

            // Prune
            if (value <= alpha)
                return value;
            beta = Math.min(beta, value);
        }
        return value;
    }
}
