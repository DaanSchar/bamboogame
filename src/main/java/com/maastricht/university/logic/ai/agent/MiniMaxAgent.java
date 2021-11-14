package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.minimax.tree.*;
import com.maastricht.university.logic.game.game.GameState;
import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IGameState;
import java.util.ArrayList;


public class MiniMaxAgent extends Agent{

    public ArrayList<Move> commonLegal;

    public MiniMaxAgent(IGameState gameState, int playerNumber) {
        super(gameState, playerNumber);
    }

    private boolean isMax(int player){
        if(player == super.player){
            return true;
        }else
            return false;
    }
    public void moveMiniMax(){
        while (gameState.getPlayerTurn() == player && gameState.winner() == 0) {
            determineMiniMaxMove();
        }
    }
    private void determineMiniMaxMove() {
        ArrayList<Move> maxLegalMoves;
        ArrayList<Move> minLegalMoves;
        Move actualMove;
        CreateTree newT = new CreateTree((GameState)gameState);
        Tree tree = (Tree)newT.getTree();

        if (isMax(player)) {
            maxLegalMoves = gameState.getLegalMoves(player);
            minLegalMoves = gameState.getLegalMoves(1);

            commonLegal = legalMoves(maxLegalMoves, minLegalMoves);
            actualMove = bestBranch(tree.getRoot().getMaxChild());

            if (commonLegal.contains(actualMove)) {
                gameState.move(actualMove.getX(),actualMove.getY(),player);
            }

        } else
            maxLegalMoves = gameState.getLegalMoves(2);
            minLegalMoves = gameState.getLegalMoves(player);

            commonLegal = legalMoves(maxLegalMoves, minLegalMoves);
            actualMove = bestBranch(tree.getRoot().getMinChild());

            if(commonLegal.contains(actualMove)){
                gameState.move(actualMove.getX(),actualMove.getY(),2);
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
        ITreeNode n;
        if(isMax(player)){
            n = node.getMaxChild();
        } else
            n = node.getMinChild();

        return n.getLastMove();
    }
}

