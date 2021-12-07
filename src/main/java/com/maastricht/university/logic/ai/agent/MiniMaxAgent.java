package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.minimax.tree.*;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import java.util.ArrayList;
import java.util.List;


public class MiniMaxAgent extends Agent {

    protected final int minPlayer;
    protected int maxDepth;

    public MiniMaxAgent(IGameState gameState, int playerNumber, int maxDepth) {
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
     * @param depth max depth of the tree
     * @return the best move
     */
    public Move search(int depth) {
        ITree<GameState> tree = new Tree<GameState>((GameState) gameState, 2);
        ITreeNode<GameState> root = tree.getRoot();
        int score = maxValue(root, depth);
        return root.getMaxChild().getLastMove();
    }

    /**
     * computes the maxValue of the branch and adds all children to the tree
     * @param node  root of branch
     * @param depth max depth of this branch
     * @return the maxValue of the branch
     */
    private int maxValue(ITreeNode node, int depth) {
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
            ITreeNode<GameState> newNode = (ITreeNode<GameState>) node.getChildren().get(i);
            value = Math.max(value, minValue(newNode, depth - 1));

            newNode.setScore(value);
        }
        return value;
    }

    /**
     * computes the minValue of the branch and adds all children to the tree
     * @param node  root of branch
     * @param depth max depth of this branch
     * @return the minValue of the branch
     */
    private int minValue(ITreeNode node, int depth) {
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
            ITreeNode<GameState> newNode = (ITreeNode<GameState>) node.getChildren().get(i);
            value = Math.min(value, maxValue(newNode,depth - 1));

            newNode.setScore(value);
        }
        return value;
    }
}