package com.maastricht.university.logic.ai.agent;

import com.maastricht.university.logic.ai.minimax.tree.ITreeNode;
import com.maastricht.university.logic.ai.minimax.tree.TreeNode;
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
    private Move determineMove(TreeNode node) {
        ArrayList<Move> maxLegalMoves;
        ArrayList<Move> minLegalMoves;
        Move actualMove;

        if (isMax(player)) {
            maxLegalMoves = gameState.getLegalMoves(player);
            minLegalMoves = gameState.getLegalMoves(1);

            commonLegal = legalMoves(maxLegalMoves, minLegalMoves);
            actualMove = bestBranch(node);


            if (commonLegal.contains(actualMove)) {
                return actualMove;
            }else{
                //error?
            }

        } else
            maxLegalMoves = gameState.getLegalMoves(2);
            minLegalMoves = gameState.getLegalMoves(player);

            commonLegal = legalMoves(maxLegalMoves, minLegalMoves);
            actualMove = bestBranch(node);

            if(commonLegal.contains(actualMove)){
                return actualMove;
            }else{
                //error?
            }

            return null;
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
    private Move bestBranch(TreeNode node){
        ITreeNode n;
        if(isMax(player)){
            n = node.getMaxChild();
        } else
            n = node.getMinChild();

        return n.getLastMove();
    }
}

