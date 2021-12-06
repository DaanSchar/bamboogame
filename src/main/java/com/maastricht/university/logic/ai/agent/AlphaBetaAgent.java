package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.minimax.tree.ITree;
import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.ai.minimax.tree.Tree;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;

import java.util.List;

public class AlphaBetaAgent extends Agent{

    protected final int minPlayer;
    protected int maxDepth;

    public AlphaBetaAgent(IGameState gameState, int playerNumber, int maxDepth) {
        super(gameState, playerNumber);
        if(player == 1) {
            minPlayer = 2;
        }else
            minPlayer = 1;
        this.maxDepth = maxDepth;
    }

    @Override
    public void move() {
        if (this.gameState.winner() ==0) {
            Move move = search(this.maxDepth);
            //System.out.println("Move: (" + move.getX() + ", " + move.getY() + ", " + player + ")");
            gameState.move(move.getX(), move.getY(), player);
        }
    }

    public Move search(int depth) {
        ITree<GameState> tree = new Tree<GameState>((GameState) gameState, 2);
        ITreeNode<GameState> root = tree.getRoot();
        int score = maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
        //System.out.println("MaxScore: " + root.getMaxChild().getScore());
        //System.out.println("MinScore: " + root.getMinChild().getScore());
        return root.getMaxChild().getLastMove();
    }

    private int maxValue(ITreeNode node, int alpha, int beta, int depth){
        GameState state = (GameState) node.getElement();
        if(state.isGameOver() || depth==0) {
            node.setScore(state.getPlayerScore(player));
            return state.getPlayerScore(player);
        }
        int value = Integer.MIN_VALUE;
        List<Move> moves = state.getLegalMoves(player);
        for(int i=0; i<moves.size(); i++) {
            Move move = moves.get(i);
            GameState newState = state.clone();
            newState.move(move.getX(), move.getY(), player);
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
        if(state.isGameOver() || depth==0) {
            node.setScore(state.getPlayerScore(player));
            return state.getPlayerScore(player);
        }
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
}
