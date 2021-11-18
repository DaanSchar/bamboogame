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

    public void determineMiniMaxMove() {
        ArrayList<Move> maxLegalMoves;
        ArrayList<Move> minLegalMoves;
        Move actualMove;
        CreateTree newT = new CreateTree((GameState)gameState);
        Tree tree = (Tree)newT.getTree();

        maxLegalMoves = gameState.getLegalMoves(player);
        minLegalMoves = gameState.getLegalMoves(otherPlayer);

        commonLegal = legalMoves(maxLegalMoves, minLegalMoves);
        actualMove = bestBranch(tree.getRoot().getMaxChild());

        if (commonLegal.contains(actualMove)) {
            gameState.move(actualMove.getX(),actualMove.getY(),player);
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
    private Move bestBranch(ITreeNode node){
        int min;
        int max;

        return null;

    }

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
}

