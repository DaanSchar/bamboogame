package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.minimax.tree.*;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import java.util.ArrayList;


public class MiniMaxAgent extends Agent{

    public ArrayList<Move> commonLegal;
    private int otherPlayer;

    public MiniMaxAgent(IGameState gameState, int playerNumber) {

        super(gameState, playerNumber);
        if(super.player == 1) {
            otherPlayer = 2;
        }else
            otherPlayer = 1;
    }

    public void moveMiniMax(){
        while (gameState.getPlayerTurn() == player && gameState.winner() == 0) {
            determineMiniMaxMove();
        }
    }

    private ArrayList<Move> legalMoves(ArrayList<Move> max, ArrayList<Move> min){
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        for (Move moveMin : min) {
            if (max.contains(moveMin)) {
                legalMoves.add(moveMin);
            }
        }
        return legalMoves;
    }

    public void determineMiniMaxMove() {
        /*
        ArrayList<Move> maxLegalMoves;
        ArrayList<Move> minLegalMoves;
        Move actualMove;
        CreateTree newT = new CreateTree((GameState)gameState);
        Tree tree = (Tree)newT.getTree();

        maxLegalMoves = gameState.getLegalMoves(player);
        minLegalMoves = gameState.getLegalMoves(otherPlayer);

        commonLegal = legalMoves(maxLegalMoves, minLegalMoves);
        actualMove = null;//bestBranch(tree.getRoot().getMaxChild());

        if (commonLegal.contains(actualMove)) {
            gameState.move(actualMove.getX(),actualMove.getY(),player);
        }
         */
        CreateTree newT = new CreateTree((GameState)gameState);
        Tree tree = (Tree)newT.getTree();
        ITreeNode root = tree.getRoot();
        Move bestMove = root.getMaxChild().getLastMove();
        gameState.move(bestMove.getX(), bestMove.getY(), player);
    }

    /**
     * The will return the optimal move for the maximizing player
     * @param node The current node (gameState)
     * @param depth How many positions ahead do we want to search
     * @return The value of the optimal branch
     */
    private int optimal(ITreeNode node, int depth, boolean isMax){
        int min = 1000000;
        int max = -1000000;
        int evaluation;

        if(depth == 0 || gameState.isGameOver()){
            return node.getScore();
        }
        if(isMax) {
            for(int i=0; i<node.getChildren().size(); i++){
                evaluation = optimal((ITreeNode)node.getChildren().get(i),depth-1, false);
                max = Math.max(max,evaluation);
                return max;
            }
            //this else statement may be redundant, I'm not sure because the minimax tree needs to compute both
            //the min and max values in order to find the optimal
        }else
            for(int i=0; i<node.getChildren().size(); i++){
                evaluation = optimal((ITreeNode)node.getChildren().get(i),depth-1, true);
                min = Math.min(min,evaluation);
            }

        return max;
    }
    /*
    private void depthFirstSearch(ITreeNode root){
        ArrayList<ITreeNode> visited = new ArrayList<ITreeNode>();
        visited.add(root);
        int size = root.getChildren().size();
        ITreeNode node;

        while(root.hasChildren()){
            for(int i=0; i<size; i++){
                node = (ITreeNode)root.getChildren().get(i);

                if(!visited.contains(node)){

                    depthFirstSearch(node);
                }
            }
        }
    }
     */
}

