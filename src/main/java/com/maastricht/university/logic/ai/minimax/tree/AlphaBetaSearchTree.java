package com.maastricht.university.logic.ai.minimax.tree;

import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.List;

public class AlphaBetaSearchTree {

    private int maxPlayer;
    private int minPlayer;


    public Move search(GameState state, int maxPlayer, int depth) {
        this.maxPlayer = maxPlayer;
        if(maxPlayer==2)
            this.minPlayer = 1;
        else
            this.minPlayer = 2;
        ITree<GameState> tree = new Tree<GameState>(state, 2);
        ITreeNode<GameState> root = tree.getRoot();
        int score = maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
        return root.getMaxChild().getLastMove();
    }

    private int maxValue(ITreeNode node, int alpha, int beta, int depth){
        GameState state = (GameState) node.getElement();
        if(state.isGameOver() || depth==0)
            return state.getPlayerScore(maxPlayer);

        int value = Integer.MIN_VALUE;
        List<Move> moves = state.getLegalMoves(maxPlayer);
        for(int i=0; i<moves.size(); i++) {
            Move move = moves.get(i);
            GameState newState = state.clone();
            newState.move(move.getX(), move.getY(), maxPlayer);
            node.addChild(newState, move);
            ITreeNode<GameState> newNode = (ITreeNode<GameState>) node.getChildren().get(i);
            value = Math.max(value, minValue(newNode, alpha, beta, depth-1));

            newNode.setScore(value);

            if(value >= beta)
                return value;
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    private int minValue(ITreeNode node, int alpha, int beta, int depth){
        GameState state = (GameState) node.getElement();
        if(state.isGameOver() || depth==0)
            return state.getPlayerScore(maxPlayer);

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

            if(value <= alpha)
                return value;
            beta = Math.min(beta, value);
        }
        return value;
    }

    /*
    public Move search(GameState state, int maxPlayer, int depth) {
        this.maxPlayer = maxPlayer;
        if(maxPlayer==2)
            this.minPlayer = 1;
        else
            this.minPlayer = 2;
        ITree<GameState> tree = new Tree<GameState>(state, state.getNumberOfPlayers());
        int score = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);

        //TODO: somehow get bestMove from score (use ITreeNode?)
    }

    private int maxValue(GameState state, int alpha, int beta, int depth){
        if(state.isGameOver() || depth==0)
            return state.getPlayerScore(maxPlayer);

        int value = Integer.MIN_VALUE;
        List<Move> moves = state.getLegalMoves(maxPlayer);
        for(Move move : moves) {
            GameState newState = state.clone();
            newState.move(move.getX(), move.getY(), maxPlayer);
            value = Math.max(value, minValue(newState, alpha, beta, depth-1));
            if(value >= beta)
                return value;
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    private int minValue(GameState state, int alpha, int beta, int depth){
        if(state.isGameOver() || depth==0)
            return state.getPlayerScore(maxPlayer);

        int value = Integer.MAX_VALUE;
        List<Move> moves = state.getLegalMoves(minPlayer);
        for(Move move : moves) {
            GameState newState = state.clone();
            newState.move(move.getX(), move.getY(), minPlayer);
            value = Math.min(value, maxValue(newState, alpha, beta, depth-1));
            if(value <= alpha)
                return value;
            beta = Math.min(beta, value);
        }
        return value;
    }
     */
}
